package com.example.productcategorycrud.repository;

import com.example.productcategorycrud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
