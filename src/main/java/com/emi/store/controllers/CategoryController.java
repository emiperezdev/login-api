package com.emi.store.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.emi.store.dtos.CategoryDto;
import com.emi.store.models.Category;
import com.emi.store.services.CategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/categories")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Category Controller", description = "Controlador para gestionar categorías")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @Operation(summary = "Obtener todas las categorías", description = "Este endpoint obtiene una lista de todas las categorías.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida", 
                   content = { @Content(mediaType = "application/json", 
                   schema = @Schema(implementation = CategoryDto.class)) })
  })
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CategoryDto> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @Operation(summary = "Obtener una categoría por ID", description = "Este endpoint obtiene la información de una categoría específica por su ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Categoría encontrada", 
                   content = { @Content(mediaType = "application/json", 
                   schema = @Schema(implementation = Category.class)) }),
      @ApiResponse(responseCode = "404", description = "Categoría no encontrada", 
                   content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(
      @io.swagger.v3.oas.annotations.Parameter(description = "ID de la categoría", example = "1") 
      @PathVariable Integer id) {
    return categoryService.getCategoryById(id);
  }

  @Operation(summary = "Crear una nueva categoría", description = "Este endpoint crea una nueva categoría con la información proporcionada.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Categoría creada", 
                   content = { @Content(mediaType = "application/json", 
                   schema = @Schema(implementation = Category.class)) }),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
                   content = @Content)
  })
  @PostMapping
  public ResponseEntity<Category> createCategory(
      @RequestBody 
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Información de la nueva categoría", 
                                                            content = @Content(mediaType = "application/json", 
                                                            schema = @Schema(implementation = Category.class))) 
      Category category) {
    return categoryService.saveCategory(category);
  }

  @Operation(summary = "Actualizar una categoría por ID", description = "Este endpoint actualiza la información de una categoría específica por su ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Categoría actualizada", 
                   content = { @Content(mediaType = "application/json", 
                   schema = @Schema(implementation = Category.class)) }),
      @ApiResponse(responseCode = "404", description = "Categoría no encontrada", 
                   content = @Content),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
                   content = @Content)
  })
  @PutMapping("/{id}")
  public ResponseEntity<?> updateCategoryById(
      @io.swagger.v3.oas.annotations.Parameter(description = "ID de la categoría", example = "1") 
      @PathVariable Integer id, 
      @RequestBody 
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Información actualizada de la categoría", 
                                                            content = @Content(mediaType = "application/json", 
                                                            schema = @Schema(implementation = Category.class))) 
      Category newCategory) {
    return categoryService.updateCategoryById(id, newCategory);
  }

  @Operation(summary = "Eliminar una categoría por ID", description = "Este endpoint elimina una categoría específica por su ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Categoría eliminada", 
                   content = @Content),
      @ApiResponse(responseCode = "404", description = "Categoría no encontrada", 
                   content = @Content)
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategoryById(
      @io.swagger.v3.oas.annotations.Parameter(description = "ID de la categoría", example = "1") 
      @PathVariable Integer id) {
    return categoryService.deleteCategoryById(id);
  }

}
