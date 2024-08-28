package com.todolist.reactive.user.repositories;

import com.todolist.reactive.user.models.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
}
