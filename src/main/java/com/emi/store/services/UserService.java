package com.emi.store.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emi.store.auth.AuthenticationRequest;
import com.emi.store.auth.RegisterRequest;
import com.emi.store.config.JwtService;
import com.emi.store.dtos.UserDTO;
import com.emi.store.mappers.UserMapper;
import com.emi.store.models.Role;
import com.emi.store.models.User;
import com.emi.store.repositories.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserMapper userMapper; // Inyecta el UserMapper

  public List<UserDTO> getAllUsers() {
    return userRepository.findAll().stream().map(userMapper::toUserDto).collect(Collectors.toList());
  }

  public ResponseEntity<?> getUserById(Integer id) {
    var foundUser = userRepository.findById(id);
    if (!foundUser.isPresent())
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with the given id: " + id);

    return ResponseEntity.ok().body(userMapper.toUserDto(foundUser.get()));
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
        .age(request.getAge())
        .phone(request.getPhone())
        .createdAt(LocalDate.now())
        .build();

    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    response.setHeader("Authorization", "Bearer " + jwtToken);
    return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserDto(user));
  }

  public ResponseEntity<?> authenticate(AuthenticationRequest request, HttpServletResponse response) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow();

    var jwtToken = jwtService.generateToken(user);
    response.setHeader("Authorization", "Bearer " + jwtToken);

    return ResponseEntity.status(HttpStatus.OK).body(userMapper.toUserDto(user));
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
    user.setAge(updatedUser.getAge());
    user.setPhone(updatedUser.getPhone());
    user.setEmail(updatedUser.getEmail());
    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty())
      user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

    user.setRole(Role.USER);

    userRepository.save(user);
    return ResponseEntity.ok().body(userMapper.toUserDto(user));
  }
}
