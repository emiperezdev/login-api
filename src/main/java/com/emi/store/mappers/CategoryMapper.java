package com.emi.store.mappers;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emi.store.dtos.CategoryDto;
import com.emi.store.models.Category;

@Service
public class CategoryMapper {
  
  public CategoryDto toCategoryDto(Category category) {
    return new CategoryDto(category.getId(), category.getName());
  }

  public Category toCategory(Optional<Category> optionalCategory) {
    return Category.builder()
      .id(optionalCategory.get().getId())
      .name(optionalCategory.get().getName())
      .createdAt(optionalCategory.get().getCreatedAt())
      .build();
  }

}
