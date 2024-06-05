package com.emi.loginApi.mappers;

import org.springframework.stereotype.Component;

import com.emi.loginApi.dtos.UserDTO;
import com.emi.loginApi.models.User;

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
