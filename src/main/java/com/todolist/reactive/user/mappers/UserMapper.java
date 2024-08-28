package com.todolist.reactive.user.mappers;

import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.models.dtos.UserDTO;

public class UserMapper {

    public static UserEntity toUserEntity(UserDTO userDtoToUserEntity){
        return UserEntity.builder().email(userDtoToUserEntity.getEmail()).name(userDtoToUserEntity.getName())
                .password(userDtoToUserEntity.getPassword()).build();
    }
}
