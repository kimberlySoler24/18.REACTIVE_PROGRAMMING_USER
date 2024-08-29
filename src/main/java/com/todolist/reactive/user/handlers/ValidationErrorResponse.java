package com.todolist.reactive.user.handlers;

import org.springframework.validation.Errors;

import java.util.List;

public class ValidationErrorResponse extends RuntimeException {
    public ValidationErrorResponse(Errors message) {
        super();
    }
}
