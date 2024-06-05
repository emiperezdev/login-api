package com.emi.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.store.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
  
}
