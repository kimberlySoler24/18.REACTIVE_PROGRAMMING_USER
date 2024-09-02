package com.todolist.reactive.user.models;

import com.todolist.reactive.user.models.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("users")
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role = Role.USER;
}
