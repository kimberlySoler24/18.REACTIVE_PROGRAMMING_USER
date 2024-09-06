package com.todolist.reactive.user.configurations;

import com.todolist.reactive.user.repositories.UserRepository;
import com.todolist.reactive.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {
    @Autowired
    private UserService userService;


    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userService.getUserByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with email: " + email)))
                .map(userEntity -> new User(userEntity.getEmail(), userEntity.getPassword(),
                        AuthorityUtils.createAuthorityList(userEntity.getRole().toString())));
    }
}
