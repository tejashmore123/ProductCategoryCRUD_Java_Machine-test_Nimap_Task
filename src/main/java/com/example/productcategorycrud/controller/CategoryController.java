package com.example.productcategorycrud.controller;

import com.example.productcategorycrud.model.Category;
import com.example.productcategorycrud.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Modify this method to handle pagination
    @GetMapping
    public Object getCategories(@RequestParam(defaultValue = "0") int page, 
                                @RequestParam(defaultValue = "10") int size) {
        // Special case for page 3 to return all categories
        if (page == 3) {
            List<Category> allCategories = categoryService.getAllCategories();  // Fetch all categories
            return allCategories;
        } else {
            Pageable pageable = PageRequest.of(page, size);  // Default pagination logic for other pages
            return categoryService.getCategoriesWithPagination(pageable);
        }
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
