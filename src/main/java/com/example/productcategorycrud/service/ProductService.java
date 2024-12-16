package com.example.productcategorycrud.service;

import com.example.productcategorycrud.model.Product;
import com.example.productcategorycrud.model.Category;
import com.example.productcategorycrud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    // Create a new product
    @Transactional
    public Product createProduct(Product product) {
        // Check if the category exists
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new IllegalArgumentException("Category information is missing.");
        }

        // Fetch the category from the database to ensure it exists
        Category category = categoryService.getCategoryById(product.getCategory().getId());
        if (category == null) {
            throw new RuntimeException("Category not found for id: " + product.getCategory().getId());
        }

        // Set the valid category to the product
        product.setCategory(category);

        // Save and return the product
        return productRepository.save(product);
    }

    // Retrieve all products with pagination
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    // Retrieve all products without pagination
    public List<Product> getAllProductsWithoutPagination() {
        return productRepository.findAll();
    }

    // Get a product by its ID
    public Product getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // Update an existing product
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        // Fetch the existing product
        Product existingProduct = getProductById(id);

        // If the category ID is different in the request, fetch and set the new category
        if (updatedProduct.getCategory() != null && updatedProduct.getCategory().getId() != null) {
            Category category = categoryService.getCategoryById(updatedProduct.getCategory().getId());
            if (category == null) {
                throw new RuntimeException("Category not found for id: " + updatedProduct.getCategory().getId());
            }
            existingProduct.setCategory(category);
        }

        // Update other fields
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());

        // Save and return the updated product
        return productRepository.save(existingProduct);
    }

    // Delete a product by its ID
    public void deleteProduct(Long id) {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }
}
