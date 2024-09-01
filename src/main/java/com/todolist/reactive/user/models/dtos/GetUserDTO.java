package com.todolist.reactive.user.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class GetUserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<TaskDTO> tasks;
}
