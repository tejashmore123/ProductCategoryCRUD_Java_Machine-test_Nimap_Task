package com.example.productcategorycrud.controller;

import com.example.productcategorycrud.model.Product;
import com.example.productcategorycrud.model.Category;
import com.example.productcategorycrud.service.ProductService;
import com.example.productcategorycrud.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Get products with pagination
    @GetMapping
    public Object getProducts(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        if (page == 2) {
            return productService.getAllProductsWithoutPagination();
        }
        return productService.getAllProducts(PageRequest.of(page - 1, size));
    }

    // Create a new product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Get a product by its ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Update an existing product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        // Log the request body to ensure it's being received correctly
        System.out.println("Received product: " + product);

        // Ensure category_id is provided in the request body
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new IllegalArgumentException("Category information is missing in the request.");
        }

        // Fetch the category from the database to ensure it exists
        Category category = categoryService.getCategoryById(product.getCategory().getId());
        if (category == null) {
            throw new RuntimeException("Category not found for id: " + product.getCategory().getId());
        }

        // Set the valid category to the product
        product.setCategory(category);

        // Proceed to update the product
        return productService.updateProduct(id, product);
    }

    // Delete a product by its ID
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
