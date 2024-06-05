package com.emi.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.store.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  
}
