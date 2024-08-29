package com.todolist.reactive.user.handlers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.Errors;
@Getter
@Setter
public class ValidationException extends RuntimeException {
    private Errors errors;
    private String errorMessage;

    public ValidationException(String errorsMessage) {
        this.errorMessage = errorsMessage;
    }

    public ValidationException(Errors errors) {
        this.errors = errors;
    }

}