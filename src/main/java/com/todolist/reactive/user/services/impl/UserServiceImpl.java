package com.todolist.reactive.user.services.impl;

import com.todolist.reactive.user.handlers.UserNotFoundException;
import com.todolist.reactive.user.handlers.ValidationException;
import com.todolist.reactive.user.mappers.UserMapper;
import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.models.dtos.UserDTO;
import com.todolist.reactive.user.repositories.UserRepository;
import com.todolist.reactive.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public Mono<UserEntity> getUserById(Long id) throws InterruptedException{

        return userRepository.findById(id).switchIfEmpty(Mono.error
                (new UserNotFoundException("User not found with id: " + id)))
                .doOnSubscribe(subscription -> logger.info("Request to retrieve user by id: {}", id))
                .doOnNext(users -> logger.info("got user by id: {}", id))
                .doOnError(error -> logger.error("Error retrieving user: {}", error.getMessage()));
    }
    @Override
    public Flux<UserEntity> getAllUsers() {

        return userRepository.findAll()
                .switchIfEmpty(Mono.error
                                (new UserNotFoundException("we don't find users on database")))
                .doOnSubscribe(subscription -> logger.info("Request to retrieve all users"))
                .doOnNext(users -> logger.debug("Retrieved user: {}", users.getName()))
                .doOnError(error -> logger.error("Error retrieving users: {}", error.getMessage()))
                .doOnComplete(() -> logger.info("Completed retrieving all users"));
    }
    @Override
    public Mono<UserEntity> createUser(UserDTO newUser) {
        validateEmail(newUser.getEmail());
        validateName(newUser.getName());
        validatePassword(newUser.getPassword());

        return userRepository.save(UserMapper.toUserEntity(newUser))
                .doOnSubscribe(subscription -> logger.info("Request to create new user: {}", newUser))
                .doOnNext(users -> logger.info("create new user: {}", newUser))
                .doOnError(error -> logger.error("Error creating user: {}", error.getMessage()));
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

                    return userRepository.save(existingUser)
                            .doOnSubscribe(subscription -> logger.info("Request to updated user by id"))
                            .doOnNext(users -> logger.debug("updated user: {}", users.getName()))
                            .doOnError(error -> logger.error("Error update users: {}", error.getMessage()));
                });
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
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name can't be empty or null");
        }
    }
    private void validatePassword(String password){
        if (password== null || password.trim().isEmpty()) {
            throw new  ValidationException("Password can't be empty or null");
        }
    }
    private void validateEmail(String email){
        int atIndex = email.indexOf('@');
        if (email.isEmpty() || email.contains(" ") || atIndex == -1
                || email.lastIndexOf('@') != atIndex) {
            throw new ValidationException("invalid Email");
        }
        int dotIndex = email.indexOf('.', atIndex);
        if (dotIndex == -1 || dotIndex == email.length() - 1) {
            throw new ValidationException("invalid Email");
        }
        int rest = dotIndex - atIndex;
        if(atIndex < 1 || rest < 2){
            throw new ValidationException("invalid Email");
        };
    }

}
