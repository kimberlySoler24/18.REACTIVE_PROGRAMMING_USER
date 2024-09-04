package com.todolist.reactive.user.configurations;

import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


//@Service
//public class CustomUserDetailsService implements ReactiveUserDetailsService {
//    @Autowired
//    private  UserRepository userRepository;
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Mono<UserDetails> findByUsername(String email) {
//        return userRepository.findByEmail(email)
//                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found with username: " + email)))
//                .map(userEntity -> new User(userEntity.getEmail(), userEntity.getPassword(),
//                        AuthorityUtils.createAuthorityList(userEntity.getRole().toString())));
//    }
//}
