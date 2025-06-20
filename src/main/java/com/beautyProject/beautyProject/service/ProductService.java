package com.beautyProject.beautyProject.service;
import com.beautyProject.beautyProject.model.entity.Product;
import com.beautyProject.beautyProject.model.entity.ProductCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    List<Product> getAllProducts();
    List<Product> getAllProducts(Pageable pageable);
    Product createProduct(Product product);
    Product getProductById(Long productId);
    Product updateProduct(Product product);
    void deleteProduct(Long productId);
    Product getProductByName(String name);
    List<Product> getProductsBySkinType(Long skinTypeId);

    // Əlavələr
    List<Product> getProductsByCategory(ProductCategory category);
    List<Product> getProductsByCategory(String categoryName);
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> searchProducts(String keyword);
    List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice);
    List<Product> getTopSellingProducts(int limit);
}