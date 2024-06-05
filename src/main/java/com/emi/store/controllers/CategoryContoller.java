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

@RestController
@RequestMapping("/api/categories")
public class CategoryContoller {

  @Autowired
  private CategoryService categoryService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CategoryDto> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCatetgoryById(@PathVariable Integer id) {
    return categoryService.getCategoryById(id);
  }

  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    return categoryService.saveCategory(category);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCategoryById(@PathVariable Integer id, @RequestBody Category newCategory) {
    return categoryService.updateCategoryById(id, newCategory);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategoryById(@PathVariable Integer id) {
    return categoryService.deleteCategoryById(id);
  }

}