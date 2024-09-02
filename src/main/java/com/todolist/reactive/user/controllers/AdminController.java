package com.todolist.reactive.user.controllers;

import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.models.dtos.GetUserDTO;
import com.todolist.reactive.user.models.dtos.UserDTO;
import com.todolist.reactive.user.services.AdminService;
import com.todolist.reactive.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/{id}")
    public Mono<GetUserDTO> getUserById(@PathVariable Long id) throws InterruptedException{
        return adminService.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    public Flux<GetUserDTO> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PostMapping("/createUser")
    public Mono<UserEntity> createUser(@RequestBody UserDTO user) throws InterruptedException{
        return adminService.createUser(user);
    }

    @PutMapping("/updateUser/{id}")
    public Mono<UserEntity> updateUser(@RequestBody UserEntity user) throws InterruptedException{
        return adminService.updateUser(user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable Long id) throws InterruptedException{
        return adminService.deleteUser(id).then(Mono.just(ResponseEntity.ok("Eliminado correctamente")));
    }
}



