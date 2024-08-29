package com.todolist.reactive.user.validations;

import com.todolist.reactive.user.models.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity user = (UserEntity) target;
        if (user.getName() == null || user.getName().isEmpty()) {
            errors.rejectValue("name", "user.name.empty", "User name cannot be empty");
        }

      if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
          errors.rejectValue("password", "user.password.empty", "password cannot be empty");
      }
      int atIndex = user.getEmail().indexOf('@');
      int dotIndex = user.getEmail().indexOf('.', atIndex);
        int restDotAndAtIndex = dotIndex - atIndex;
        if (user.getEmail().isEmpty() || user.getEmail().contains(" ") || atIndex == -1
                || user.getEmail().lastIndexOf('@') != atIndex
        || dotIndex == -1 || dotIndex == user.getEmail().length() - 1
        || atIndex < 1 || restDotAndAtIndex < 2) {
            errors.rejectValue("email", "user.email.empty", "email cannot be empty");
        }
    }
}
