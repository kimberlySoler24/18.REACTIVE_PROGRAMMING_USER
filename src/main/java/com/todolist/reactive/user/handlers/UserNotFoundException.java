package com.todolist.reactive.user.handlers;

import com.todolist.reactive.user.models.UserEntity;
import reactor.core.publisher.Mono;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
       super(message);
    }
}
