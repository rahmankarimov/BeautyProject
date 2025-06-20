package com.beautyProject.beautyProject.controller;

import com.beautyProject.beautyProject.model.entity.Product;
import com.beautyProject.beautyProject.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@Validated
@Slf4j
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("REST request to get all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        logger.info("REST request to create product: {}", product.getName());
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        logger.info("REST request to get product by ID: {}", id);
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        logger.info("REST request to update product with ID: {}", id);
        product.setId(id);
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @GetMapping("/skin-type/{skinTypeId}")
    public ResponseEntity<List<Product>> getProductsBySkinType(@PathVariable Long skinTypeId) {
        logger.info("REST request to get products by skin type ID: {}", skinTypeId);
        return ResponseEntity.ok(productService.getProductsBySkinType(skinTypeId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        logger.info("REST request to get products by category ID: {}", categoryId);
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/category/name/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategoryName(@PathVariable String categoryName) {
        logger.info("REST request to get products by category name: {}", categoryName);
        return ResponseEntity.ok(productService.getProductsByCategory(categoryName));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        logger.info("REST request to search products with keyword: {}", keyword);
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam @Min(0) Double minPrice,
            @RequestParam @Min(0) Double maxPrice) {
        logger.info("REST request to get products in price range: {} - {}", minPrice, maxPrice);
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/top-selling")
    public ResponseEntity<List<Product>> getTopSellingProducts(@RequestParam(defaultValue = "10") int limit) {
        logger.info("REST request to get top {} selling products", limit);
        return ResponseEntity.ok(productService.getTopSellingProducts(limit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        logger.info("REST request to delete product with ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok(Map.of("message", "Product deleted successfully!"));
    }
}