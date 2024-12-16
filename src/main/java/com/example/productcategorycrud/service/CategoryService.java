package com.example.productcategorycrud.service;

import com.example.productcategorycrud.model.Category;
import com.example.productcategorycrud.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;  // Make sure this is imported
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Method to get all categories (without pagination)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();  // Fetch all categories when page 3 is requested
    }

    // Method to get categories with pagination (used for other pages)
    public Page<Category> getCategoriesWithPagination(Pageable pageable) {
        return categoryRepository.findAll(pageable);  // Fetch paginated categories
    }

    // Method to create a category
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Method to get category by ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Method to update a category
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = getCategoryById(id);
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            return categoryRepository.save(existingCategory);
        }
        return null;
    }

    // Method to delete a category by ID
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
