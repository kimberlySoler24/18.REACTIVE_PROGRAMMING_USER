package com.todolist.reactive.user.services;

import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.models.dtos.UserDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserEntity> getUserById(Long id) throws InterruptedException;
    Flux<UserEntity> getAllUsers();
    Mono<UserEntity> createUser(UserDTO createUser);
    Mono<Void> deleteUser(Long id);
    Mono<UserEntity> updateUser(UserEntity updateuser);
}
