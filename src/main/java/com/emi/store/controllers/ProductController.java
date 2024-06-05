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

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/category/{id}")
  public List<Product> getProductsByCategoryId(@PathVariable Integer id) {
    return productService.getProductsByCategoryId(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Product> getAllProducts() {
    return productService.getProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
    return productService.getProductById(id);
  }

  @PostMapping
  public ResponseEntity<Product> saveProduct(@RequestBody ProductDto dto) {
    return productService.saveProduct(dto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateProductById(@PathVariable Integer id, @RequestBody ProductDto dto) {
    return productService.updateProductById(id, dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProductById(@PathVariable Integer id) {
    return productService.deleteProductById(id);
  }

}
