package com.ruben.todoapp.mappers;

import org.springframework.stereotype.Component;

import com.ruben.todoapp.dtos.UserDTO;
import com.ruben.todoapp.models.User;

@Component
public class UserMapper {

  public UserDTO toUserDto(User user) {

    return new UserDTO(
      user.getId(),
      user.getFirstName(),
      user.getLastName(),
      user.getEmail(),
      user.getRole()
    );
  }
  
}
