package com.emi.store.services;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.emi.store.dtos.CategoryDto;
import com.emi.store.mappers.CategoryMapper;
import com.emi.store.models.Category;
import com.emi.store.repositories.CategoryRepository;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryMapper categoryMapper;

  public List<CategoryDto> getAllCategories() {
    return categoryRepository.findAll()
      .stream()
      .map(categoryMapper::toCategoryDto)
      .collect(Collectors.toList());
  }

  public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
    Optional<Category> category = categoryRepository.findById(id);
    return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
    category.setCreatedAt(LocalDate.now());
    categoryRepository.save(category);

    return ResponseEntity.status(HttpStatus.CREATED).body(category);
  }

  public ResponseEntity<?> updateCategoryById(@PathVariable Integer id, @RequestBody Category newCategory) {
    Optional<Category> currentCategory = categoryRepository.findById(id);
    if (currentCategory.isPresent()) {
      Category updatedCategory = currentCategory.get();
      updatedCategory.setName(newCategory.getName());
      updatedCategory.setCreatedAt(updatedCategory.getCreatedAt());
      categoryRepository.save(updatedCategory);

      return ResponseEntity.ok(updatedCategory);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with the given id: " + id);
  }

  public ResponseEntity<String> deleteCategoryById(@PathVariable Integer id) {
    Optional<Category> category = categoryRepository.findById(id);
    if (category.isPresent()) {
      categoryRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with the given id: " + id);
  }
  
}
