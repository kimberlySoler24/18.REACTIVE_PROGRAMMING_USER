package com.todolist.reactive.user.controllers;

import com.todolist.reactive.user.configurations.JwtUtils;
import com.todolist.reactive.user.models.dtos.LoginUserDTO;
import com.todolist.reactive.user.models.dtos.UserDTO;
import com.todolist.reactive.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    ReactiveAuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Mono<?> registerUser(@RequestBody UserDTO registrationDto) throws Exception {

        return userService.createUser(registrationDto);
    }
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> loginUser(@RequestBody LoginUserDTO loginRequest) throws Exception{

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))
                .map(auth -> {
                    UserDetails userDetails = (UserDetails) auth.getPrincipal();
                    String jwt = jwtUtils.generateToken(userDetails.getUsername());
                    return ResponseEntity.ok(jwt);
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
