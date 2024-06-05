package com.emi.store.dtos;

import java.time.LocalDate;

import com.emi.store.models.Role;

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
  private LocalDate createdAt;
  private String phone;
  private int age;
  private Role role;
  
}
