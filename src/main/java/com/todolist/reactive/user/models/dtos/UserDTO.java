package com.todolist.reactive.user.models.dtos;

import com.todolist.reactive.user.models.UserEntity;
import lombok.*;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String password;

}
