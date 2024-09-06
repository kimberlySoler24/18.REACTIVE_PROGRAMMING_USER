package com.todolist.reactive.user.controllers;

import com.todolist.reactive.user.handlers.UserNotFoundException;

import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.models.dtos.GetUserDTO;
import com.todolist.reactive.user.models.dtos.UserDTO;
import com.todolist.reactive.user.repositories.UserRepository;
import com.todolist.reactive.user.services.UserService;
import com.todolist.reactive.user.utils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/{id}")
    public Mono<GetUserDTO> getUserById(@PathVariable Long id) throws InterruptedException{
        return userService.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    public Flux<GetUserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/createUser")
    public Mono<UserEntity> createUser(@RequestBody UserDTO user) throws InterruptedException{
        return userService.createUser(user);
    }

    @PutMapping("/updateUser/{id}")
    public Mono<UserEntity> updateUser(@RequestBody UserEntity user) throws InterruptedException{
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable Long id) throws InterruptedException{
        return userService.deleteUser(id).then(Mono.just(ResponseEntity.ok("Eliminado correctamente")));
    }

    @GetMapping("/current")
    public Mono<UserDTO> getCurrent(ServerWebExchange exchange){
        String username = HeaderUtil.extractUsername(exchange);
        return userRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                .map(userEntity -> new UserDTO(userEntity.getName(), userEntity.getEmail(), userEntity.getPassword()));
    }
}



