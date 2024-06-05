package com.emi.store.controllers;

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

import com.emi.store.auth.AuthenticationRequest;
import com.emi.store.auth.RegisterRequest;
import com.emi.store.dtos.UserDto;
import com.emi.store.models.User;
import com.emi.store.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public List<UserDto> getAllUsers() {
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
