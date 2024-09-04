package com.todolist.reactive.user.controllers;

import com.todolist.reactive.user.models.dtos.LoginUserDTO;
import com.todolist.reactive.user.models.dtos.UserDTO;
import com.todolist.reactive.user.services.AuthenticationService;
import com.todolist.reactive.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("/api/auth")
//public class AuthenticationController {
//    @Autowired
//    private AuthenticationService authenticationService;
//    @PostMapping("/register")
//    public Mono<ResponseEntity<String>> registerUser(@RequestBody UserDTO registrationDto) throws Exception {
//        return authenticationService.registerUser(registrationDto)
//                .then(Mono.just(ResponseEntity.ok("Eliminado correctamente")));
//    }
//
//    @PostMapping("/login")
//    public Mono<ResponseEntity<String>> loginUser(@RequestBody LoginUserDTO loginRequest) throws Exception{
//        return authenticationService.loginUser(loginRequest)
//                .map(jwt -> ResponseEntity.ok(jwt));
//    }
//}
