package com.emi.store.mappers;

import org.springframework.stereotype.Service;

import com.emi.store.dtos.ProductDto;
import com.emi.store.models.Category;
import com.emi.store.models.Product;

@Service
public class ProductMapper {
  
  public Product toProduct(ProductDto dto) {
    var category = new Category();
    category.setId(dto.categoryId());
    
    return Product.builder()
      .name(dto.name())
      .stock(dto.stock())
      .description(dto.description())
      .price(dto.price())
      .createdAt(dto.createdAt())
      .category(category)
      .build();
  }

}
