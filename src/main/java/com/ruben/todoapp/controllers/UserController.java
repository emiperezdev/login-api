package com.ruben.todoapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.todoapp.auth.AuthenticationRequest;
import com.ruben.todoapp.auth.RegisterRequest;
import com.ruben.todoapp.dtos.UserDTO;
import com.ruben.todoapp.models.User;
import com.ruben.todoapp.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo-app/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public List<UserDTO> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Integer id) {
    return userService.getUserById(id);
  }
  
  @PostMapping
  public ResponseEntity<?> register(
    @RequestBody RegisterRequest request,
    HttpServletResponse response
  ) {
    return userService.register(request, response);
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticate(
    @RequestBody AuthenticationRequest request,
    HttpServletResponse response
  ) {
    return userService.authenticate(request, response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
    return userService.deleteUserById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUserById(@PathVariable Integer id, @RequestBody User updatedUser) {
    return userService.updateUserById(id, updatedUser);
  }

}
