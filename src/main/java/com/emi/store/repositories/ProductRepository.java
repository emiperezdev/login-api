package com.emi.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.store.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  
  List<Product> findByCategoryId(Integer categoryId);

}
