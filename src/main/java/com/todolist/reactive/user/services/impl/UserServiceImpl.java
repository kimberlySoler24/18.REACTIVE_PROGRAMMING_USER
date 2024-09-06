package com.todolist.reactive.user.services.impl;

import com.todolist.reactive.user.configurations.ConfigPasswordEncoder;
import com.todolist.reactive.user.handlers.UserNotFoundException;
import com.todolist.reactive.user.handlers.ValidationException;
import com.todolist.reactive.user.handlers.ValidationOnlyEmailException;
import com.todolist.reactive.user.mappers.UserMapper;
import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.models.dtos.GetUserDTO;
import com.todolist.reactive.user.models.dtos.TaskDTO;
import com.todolist.reactive.user.models.dtos.UserDTO;
import com.todolist.reactive.user.repositories.UserRepository;
import com.todolist.reactive.user.services.UserService;
import com.todolist.reactive.user.validations.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/tasks").build();
    @Override
    public Mono<GetUserDTO> getUserById(Long id) throws InterruptedException{
        return userRepository.findById(id).flatMap( user ->{
                    GetUserDTO getUserDto = new GetUserDTO();
                    getUserDto.setName(user.getName());
                    getUserDto.setId(user.getId());
                    getUserDto.setEmail(user.getEmail());
                    return getTasksFromUser(user.getEmail())
                            .collectList()
                            .flatMap(taskDTOS -> {
                                getUserDto.setTasks(taskDTOS);
                                return Mono.just(getUserDto);
                                    });
                }).switchIfEmpty(Mono.error
                (new UserNotFoundException("User not found with id: " + id)))
                .doOnSubscribe(subscription -> logger.info("Request to retrieve user by id: {}", id))
                .doOnNext(users -> logger.info("got user by id: {}", id))
                .doOnError(error -> logger.error("Error retrieving user: {}", error.getMessage()));
    }
    @Override
    public Flux<GetUserDTO> getAllUsers() {

        return userRepository.findAll().flatMap( user ->
            getTasksFromUser(user.getEmail())
                    .collectList()
                    .flatMap( taskDTOS -> {
                        GetUserDTO getUserDto = new GetUserDTO();
                        getUserDto.setName(user.getName());
                        getUserDto.setId(user.getId());
                        getUserDto.setEmail(user.getEmail());
                        getUserDto.setTasks(taskDTOS);
                        return Mono.just(getUserDto);
                    })
                ).switchIfEmpty(Mono.error
                                (new UserNotFoundException("we don't find users on database")))
                .doOnSubscribe(subscription -> logger.info("Request to retrieve all users"))
                .doOnNext(users -> logger.debug("Retrieved user: {}", users.getName()))
                .doOnError(error -> logger.error("Error retrieving users: {}", error.getMessage()))
                .doOnComplete(() -> logger.info("Completed retrieving all users"));
    }
    @Override
    public Mono<UserEntity> createUser(UserDTO newUser) {
        return Mono.just(UserMapper.toUserEntity(newUser))
                .doOnSubscribe(subscription -> logger.info("Request to create new user: {}", newUser))
                .flatMap(user -> {
                    user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                    Errors errors = new BeanPropertyBindingResult(user, "user");
                    userValidator.validate(user, errors);

                    if (errors.hasErrors()) {
                        String errorMessage = errors.getAllErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining(", "));
                        logger.error("Validation error for user: {}", errorMessage);
                        return Mono.error(new ValidationException(errorMessage));
                    }

                    return userRepository.save(user)
                            .doOnNext(savedUser -> logger.info("Created new user: {}", savedUser))
                            .doOnError(error -> logger.error("Error creating user: {}", error.getMessage()));
                })
                .doOnError(error -> {
                    if (!(error instanceof ValidationException)) {
                        logger.error("Unexpected error creating user: {}", error.getMessage());
                    }
                });
    }
    @Override
    public Mono<UserEntity> updateUser(UserEntity updateUser) {
        return userRepository.findById(updateUser.getId())
                .switchIfEmpty(Mono.error
                                (new UserNotFoundException("User not found with id: " + updateUser.getId())))
                .flatMap(existingUser -> {
                    if (updateUser.getName() != null) existingUser.setName(updateUser.getName());
                    if (updateUser.getEmail() != null){
                        validateEmail(updateUser.getEmail());
                        existingUser.setEmail(updateUser.getEmail());
                    }
                    if (updateUser.getPassword() != null) existingUser.setPassword(updateUser.getPassword());

                    return userRepository.save(existingUser);
                }).doOnSubscribe(subscription -> logger.info("Request to updated user by id: {}", updateUser.getName()))
                .doOnNext(users -> logger.debug("updated user: {}", updateUser.getName()))
                .doOnError(error -> logger.error("Error update users: {}", error.getMessage()));
    }

    @Override
    public Mono<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email).switchIfEmpty(Mono.error
                (new UserNotFoundException("User not found with that email: " + email)))
                .doOnSubscribe(subscription -> logger.info("Request to get user by email: {}", email))
                .doOnNext(users -> logger.debug("get user by email: {}", email))
                .doOnError(error -> logger.error("Error get user by email: {}", error.getMessage()));
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return userRepository.findById(id)
                .flatMap(userRepository::delete).switchIfEmpty(Mono.error
                        (new UserNotFoundException("User not found with id: " + id)))
                .doOnSubscribe(subscription -> logger.info("delete user"))
                .doOnNext(users -> logger.debug("deleted user with ID: {}", id))
                .doOnError(error -> logger.error("Error deleting user: {}", error.getMessage()));
    }

    private void validateEmail(String email){
        int atIndex = email.indexOf('@');
        if (email.isEmpty() || email.contains(" ") || atIndex == -1
                || email.lastIndexOf('@') != atIndex) {
            throw new ValidationOnlyEmailException("invalid Email");
        }
        int dotIndex = email.indexOf('.', atIndex);
        if (dotIndex == -1 || dotIndex == email.length() - 1) {
            throw new ValidationOnlyEmailException("invalid Email");
        }
        int rest = dotIndex - atIndex;
        if(atIndex < 1 || rest < 2){
            throw new ValidationOnlyEmailException("invalid Email");
        };
    }

    private Flux<TaskDTO> getTasksFromUser(String email){
        return webClient.get().uri("/user/" + email)
                .retrieve().bodyToFlux(TaskDTO.class);
    }

}
