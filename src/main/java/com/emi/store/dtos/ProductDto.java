package com.emi.store.dtos;

import java.time.LocalDate;

public record ProductDto(
  String name,
  String description,
  int stock,
  Double price,
  Integer categoryId,
  LocalDate createdAt
) {
  
}
