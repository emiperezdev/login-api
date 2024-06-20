package com.emi.store.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.emi.store.dtos.ProductDto;
import com.emi.store.models.Product;
import com.emi.store.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/products")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Product Controller", description = "Controlador para gestionar los productos")
public class ProductController {

  @Autowired
  private ProductService productService;

  @Operation(summary = "Obtener productos por ID de categoría", 
             description = "Este endpoint obtiene una lista de productos asociados a una categoría específica.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Productos obtenidos",
                   content = { @Content(mediaType = "application/json",
                   schema = @Schema(implementation = Product.class)) }),
      @ApiResponse(responseCode = "404", description = "Categoría no encontrada", 
                   content = @Content)
  })
  @GetMapping("/category/{id}")
  public List<Product> getProductsByCategoryId(
      @Parameter(description = "ID de la categoría", example = "1")
      @PathVariable Integer id) {
    return productService.getProductsByCategoryId(id);
  }

  @Operation(summary = "Obtener todos los productos", 
             description = "Este endpoint obtiene una lista de todos los productos disponibles.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de productos obtenida",
                   content = { @Content(mediaType = "application/json",
                   schema = @Schema(implementation = Product.class)) })
  })
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Product> getAllProducts() {
    return productService.getProducts();
  }

  @Operation(summary = "Obtener un producto por ID", 
             description = "Este endpoint obtiene la información de un producto específico por su ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Producto encontrado",
                   content = { @Content(mediaType = "application/json",
                   schema = @Schema(implementation = Product.class)) }),
      @ApiResponse(responseCode = "404", description = "Producto no encontrado", 
                   content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(
      @Parameter(description = "ID del producto", example = "1")
      @PathVariable Integer id) {
    return productService.getProductById(id);
  }

  @Operation(summary = "Crear un nuevo producto", 
             description = "Este endpoint crea un nuevo producto con la información proporcionada.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Producto creado",
                   content = { @Content(mediaType = "application/json",
                   schema = @Schema(implementation = Product.class)) }),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
                   content = @Content)
  })
  @PostMapping
  public ResponseEntity<Product> saveProduct(
      @RequestBody 
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO del producto", 
                                                            content = @Content(mediaType = "application/json",
                                                            schema = @Schema(implementation = ProductDto.class),
                                                            examples = @ExampleObject(value = "{ \"name\": \"Producto 1\", \"description\": \"Descripción del producto\", \"price\": 100.0, \"categoryId\": 1 }")))
      ProductDto dto) {
    return productService.saveProduct(dto);
  }

  @Operation(summary = "Actualizar un producto por ID", 
             description = "Este endpoint actualiza la información de un producto específico por su ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Producto actualizado",
                   content = { @Content(mediaType = "application/json",
                   schema = @Schema(implementation = Product.class)) }),
      @ApiResponse(responseCode = "404", description = "Producto no encontrado", 
                   content = @Content),
      @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
                   content = @Content)
  })
  @PutMapping("/{id}")
  public ResponseEntity<?> updateProductById(
      @Parameter(description = "ID del producto", example = "1")
      @PathVariable Integer id, 
      @RequestBody 
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "DTO del producto actualizado", 
                                                            content = @Content(mediaType = "application/json",
                                                            schema = @Schema(implementation = ProductDto.class),
                                                            examples = @ExampleObject(value = "{ \"name\": \"Producto 1 Actualizado\", \"description\": \"Descripción actualizada del producto\", \"price\": 150.0, \"categoryId\": 1 }")))
      ProductDto dto) {
    return productService.updateProductById(id, dto);
  }

  @Operation(summary = "Eliminar un producto por ID", 
             description = "Este endpoint elimina un producto específico por su ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Producto eliminado", 
                   content = @Content),
      @ApiResponse(responseCode = "404", description = "Producto no encontrado", 
                   content = @Content)
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProductById(
      @Parameter(description = "ID del producto", example = "1")
      @PathVariable Integer id) {
    return productService.deleteProductById(id);
  }

}
