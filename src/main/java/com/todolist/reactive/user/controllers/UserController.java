package com.todolist.reactive.user.controllers;

import com.todolist.reactive.user.handlers.UserNotFoundException;
import com.todolist.reactive.user.handlers.ValidationException;
import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.models.dtos.GetUserDTO;
import com.todolist.reactive.user.models.dtos.UserDTO;
import com.todolist.reactive.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Mono<GetUserDTO> getUserById(@PathVariable Long id) throws InterruptedException{
        return userService.getUserById(id);
    }

    @GetMapping
    public Flux<GetUserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public Mono<UserEntity> createUser(@RequestBody UserDTO user) throws InterruptedException{
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public Mono<UserEntity> updateUser(@RequestBody UserEntity user) throws InterruptedException{
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable Long id) throws InterruptedException{
        return userService.deleteUser(id).then(Mono.just(ResponseEntity.ok("Eliminado correctamente")));
    }
}



