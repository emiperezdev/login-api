package com.ruben.todoapp.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ruben.todoapp.auth.AuthenticationRequest;
import com.ruben.todoapp.auth.RegisterRequest;
import com.ruben.todoapp.config.JwtService;
import com.ruben.todoapp.models.Role;
import com.ruben.todoapp.models.User;
import com.ruben.todoapp.repositories.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public ResponseEntity<?> getUserById(Integer id) {
    var foundUser = userRepository.findById(id);
    if (!foundUser.isPresent())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with the given id: " + id);

    return ResponseEntity.ok().body(foundUser);
  }

  public ResponseEntity<?> register(RegisterRequest request, HttpServletResponse response) { 
    var foundUser = userRepository.findByEmail(request.getEmail());
    if (foundUser.isPresent())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already registered.");

    var user = User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();

    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    response.setHeader("Authorization", "Bearer " + jwtToken);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  public ResponseEntity<?> authenticate(AuthenticationRequest request, HttpServletResponse response) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow();

    var jwtToken = jwtService.generateToken(user);
    response.setHeader("Authorization", "Bearer " + jwtToken);

    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  public ResponseEntity<?> deleteUserById(Integer id) {
    var foundUser = userRepository.findById(id);
    if (!foundUser.isPresent()) 
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with the given id: " + id);

    userRepository.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  public ResponseEntity<?> updateUserById(Integer id, User updatedUser) {
    var foundUser = userRepository.findById(id);
    if (!foundUser.isPresent()) 
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with the given id: " + id);

    User user = foundUser.get();
    
    if (!user.getEmail().equals(updatedUser.getEmail())) {
      var userWithSameEmail = userRepository.findByEmail(updatedUser.getEmail());
      if (userWithSameEmail.isPresent()) 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already registered by another user.");
    }

    user.setFirstName(updatedUser.getFirstName());
    user.setLastName(updatedUser.getLastName());
    user.setEmail(updatedUser.getEmail());
    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) 
      user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    
    user.setRole(Role.USER);

    userRepository.save(user);
    return ResponseEntity.ok().body(user);
  }

}
