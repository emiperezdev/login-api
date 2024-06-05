package com.emi.store.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.emi.store.dtos.ProductDto;
import com.emi.store.mappers.CategoryMapper;
import com.emi.store.mappers.ProductMapper;
import com.emi.store.models.Product;
import com.emi.store.repositories.CategoryRepository;
import com.emi.store.repositories.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductMapper productMapper;

  @Autowired
  private CategoryMapper categoryMapper;

  public List<Product> getProductsByCategoryId(@PathVariable Integer categoryId) {
    return productRepository.findByCategoryId(categoryId);
  }

  public List<Product> getProducts() {
    return productRepository.findAll();
  }

  public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
    Optional<Product> product = productRepository.findById(id);
    return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  public ResponseEntity<Product> saveProduct(@RequestBody ProductDto dto) {
    if (!existsCategory(dto.categoryId()))
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    var product = productMapper.toProduct(dto);
    product.setCreatedAt(LocalDate.now());
    Product savedProduct = productRepository.save(product);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
  }

  public ResponseEntity<?> updateProductById(@PathVariable Integer id, @RequestBody ProductDto dto) {
    if (!existsCategory(dto.categoryId()))
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    Optional<Product> currentProduct = productRepository.findById(id);
    if (currentProduct.isPresent()) {
      Product updatedProduct = currentProduct.get();
      var optionalCategory = categoryRepository.findById(dto.categoryId());
      var category = categoryMapper.toCategory(optionalCategory);

      updatedProduct.setName(dto.name());
      updatedProduct.setDescription(dto.description());
      updatedProduct.setStock(dto.stock());
      updatedProduct.setPrice(dto.price());
      updatedProduct.setCategory(category);
      updatedProduct.setCreatedAt(dto.createdAt());

      productRepository.save(updatedProduct);

      return ResponseEntity.ok(updatedProduct);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with the given id: " + id);
  }

  public ResponseEntity<String> deleteProductById(@PathVariable Integer id) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isPresent()) {
      productRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with the given id: " + id);
  }

  private boolean existsCategory(int id) {
    return categoryRepository.existsById(id);
  }

}