package com.emi.store.mappers;

import org.springframework.stereotype.Component;

import com.emi.store.dtos.UserDto;
import com.emi.store.models.User;

@Component
public class UserMapper {

  public UserDto toUserDto(User user) {

    return new UserDto(
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
