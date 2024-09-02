package com.todolist.reactive.user.services;

import com.todolist.reactive.user.models.dtos.LoginUserDTO;
import com.todolist.reactive.user.models.dtos.UserDTO;
import reactor.core.publisher.Mono;

public interface AuthenticationService {

    Mono<String> registerUser(UserDTO registrationDto);
    Mono<String> loginUser(LoginUserDTO loginUserDTO);
}
