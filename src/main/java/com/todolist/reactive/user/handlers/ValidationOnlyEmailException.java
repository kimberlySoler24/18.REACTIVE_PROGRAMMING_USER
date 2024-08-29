package com.todolist.reactive.user.handlers;

public class ValidationOnlyEmailException extends RuntimeException {
    public ValidationOnlyEmailException(String message) {
       super(message);
    }
}
