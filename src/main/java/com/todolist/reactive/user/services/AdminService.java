package com.todolist.reactive.user.services;

import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.models.dtos.GetUserDTO;
import com.todolist.reactive.user.models.dtos.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdminService {
    Mono<GetUserDTO> getUserById(Long id) throws InterruptedException;
    Flux<GetUserDTO> getAllUsers();
    Mono<UserEntity> createUser(UserDTO createUser);
    Mono<Void> deleteUser(Long id);
    Mono<UserEntity> updateUser(UserEntity updateuser);
}
