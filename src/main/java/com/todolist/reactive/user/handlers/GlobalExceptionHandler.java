package com.todolist.reactive.user.handlers;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    public Mono<ResponseEntity<String>> handlerNotFoundException(UserNotFoundException ex){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    @ExceptionHandler({UsernameAlreadyExistException.class})
    public Mono<ResponseEntity<String>> handlerUserAlreadyExistException(UserNotFoundException ex){
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()));
    }

    @ExceptionHandler({ValidationOnlyEmailException.class})
    public Mono<ResponseEntity<String>> ValidationOnlyEmailException(ValidationException ex){
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
    }

    @ExceptionHandler({ValidationException.class})
    public Mono<ResponseEntity<ValidationErrorResponse>> handlerUserValidationException(ValidationException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse(ex.getErrors());
        return Mono.just(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));
    }
}
