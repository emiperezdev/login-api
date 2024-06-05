package com.emi.store.mappers;

import org.springframework.stereotype.Component;

import com.emi.store.dtos.UserDTO;
import com.emi.store.models.User;

@Component
public class UserMapper {

  public UserDTO toUserDto(User user) {

    return new UserDTO(
      user.getId(),
      user.getFirstName(),
      user.getLastName(),
      user.getEmail(),
      user.getCreatedAt(),
      user.getPhone(),
      user.getAge(),
      user.getRole()
    );
  }
  
}
