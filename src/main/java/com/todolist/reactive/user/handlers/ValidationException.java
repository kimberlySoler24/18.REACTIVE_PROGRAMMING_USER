package com.todolist.reactive.user.handlers;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}