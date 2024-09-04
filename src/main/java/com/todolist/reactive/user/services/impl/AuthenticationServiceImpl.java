package com.todolist.reactive.user.services.impl;

//import com.todolist.reactive.user.configurations.JwtUtils;
import com.todolist.reactive.user.handlers.UsernameAlreadyExistException;
import com.todolist.reactive.user.models.Role;
import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.models.dtos.LoginUserDTO;
import com.todolist.reactive.user.models.dtos.UserDTO;
import com.todolist.reactive.user.repositories.UserRepository;
import com.todolist.reactive.user.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

//@Service
//public class AuthenticationServiceImpl implements AuthenticationService {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private ReactiveAuthenticationManager authenticationManager;
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @Override
//    public Mono<String> registerUser(UserDTO registrationDto) {
//        return userRepository.findByEmail(registrationDto.getEmail())
//                .flatMap(existingUser -> {
//                    return Mono.error(new UsernameAlreadyExistException("User already exist"));
//                })
//                .switchIfEmpty(Mono.defer(() -> {
//                    UserEntity newUser = new UserEntity();
//                    newUser.setEmail(registrationDto.getEmail());
//                    newUser.setPassword(registrationDto.getPassword());
//                    newUser.setRole(Role.USER);
//                    return userRepository.save(newUser).then();
//                })).then(Mono.just("Registro de usuario exitoso!!"));
//    }
//
//    @Override
//    public Mono<String> loginUser(LoginUserDTO loginUserDTO) {
//        {
//            return authenticationManager.authenticate(
//                            new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(),
//                                    loginUserDTO.getPassword())
//                    )
//                    .flatMap(authentication -> {
//                        return ReactiveSecurityContextHolder.getContext()
//                                .doOnNext(securityContext -> securityContext.setAuthentication(authentication))
//                                .then(Mono.just(authentication));
//                    })
//                    .map(authentication -> {
//                        return jwtUtils.generateClaims(authentication.getName());
//                    })
//                    .onErrorResume(e -> Mono.error(new RuntimeException("Invalid credentials"))).block();
//        }
//    }
//}
