package com.example.productcategorycrud.repository;

import com.example.productcategorycrud.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
