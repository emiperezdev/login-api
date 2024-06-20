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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "User Controller", description = "Controlador para gestionar usuarios")
public class UserController {

  private final UserService userService;

  @Operation(summary = "Obtener todos los usuarios", description = "Este endpoint obtiene una lista de todos los usuarios registrados.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida", 
                   content = { @Content(mediaType = "application/json", 
                   schema = @Schema(implementation = UserDto.class)) })
  })
  @GetMapping
  public List<UserDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @Operation(summary = "Obtener un usuario por ID", description = "Este endpoint obtiene la información de un usuario específico por su ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario encontrado", 
                   content = { @Content(mediaType = "application/json", 
                   schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "404", description = "Usuario no encontrado", 
                   content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(
      @io.swagger.v3.oas.annotations.Parameter(description = "ID del usuario", example = "1") 
      @PathVariable Integer id) {
    return userService.getUserById(id);
  }
  
  @Operation(summary = "Registrar un nuevo usuario", description = "Este endpoint registra un nuevo usuario con la información proporcionada.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuario registrado", 
                   content = { @Content(mediaType = "application/json", 
                   schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
                   content = @Content)
  })
  @PostMapping
  public ResponseEntity<?> register(
      @RequestBody 
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Información de registro del usuario", 
                                                            content = @Content(mediaType = "application/json", 
                                                            schema = @Schema(implementation = RegisterRequest.class))) 
      RegisterRequest request,
      HttpServletResponse response) {
    return userService.register(request, response);
  }

  @Operation(summary = "Autenticar usuario", description = "Este endpoint autentica un usuario con la información de inicio de sesión proporcionada.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario autenticado", 
                   content = { @Content(mediaType = "application/json", 
                   schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "401", description = "Credenciales inválidas", 
                   content = @Content)
  })
  @PostMapping("/login")
  public ResponseEntity<?> authenticate(
      @RequestBody 
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Información de autenticación del usuario", 
                                                            content = @Content(mediaType = "application/json", 
                                                            schema = @Schema(implementation = AuthenticationRequest.class))) 
      AuthenticationRequest request,
      HttpServletResponse response) {
    return userService.authenticate(request, response);
  }

  @Operation(summary = "Eliminar un usuario por ID", description = "Este endpoint elimina un usuario específico por su ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario eliminado", 
                   content = @Content),
      @ApiResponse(responseCode = "404", description = "Usuario no encontrado", 
                   content = @Content)
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUserById(
      @io.swagger.v3.oas.annotations.Parameter(description = "ID del usuario", example = "1") 
      @PathVariable Integer id) {
    return userService.deleteUserById(id);
  }

  @Operation(summary = "Actualizar un usuario por ID", description = "Este endpoint actualiza la información de un usuario específico por su ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuario actualizado", 
                   content = { @Content(mediaType = "application/json", 
                   schema = @Schema(implementation = User.class)) }),
      @ApiResponse(responseCode = "404", description = "Usuario no encontrado", 
                   content = @Content),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
                   content = @Content)
  })
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUserById(
      @io.swagger.v3.oas.annotations.Parameter(description = "ID del usuario", example = "1") 
      @PathVariable Integer id, 
      @RequestBody 
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Información actualizada del usuario", 
                                                            content = @Content(mediaType = "application/json", 
                                                            schema = @Schema(implementation = User.class))) 
      User updatedUser) {
    return userService.updateUserById(id, updatedUser);
  }

}
