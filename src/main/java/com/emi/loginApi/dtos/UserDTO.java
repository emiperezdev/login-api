package com.emi.loginApi.dtos;

import com.emi.loginApi.models.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private Integer id;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
  
}
