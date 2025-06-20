package com.beautyProject.beautyProject.service.Impl;

import com.beautyProject.beautyProject.exception.ResourceNotFoundException;
import com.beautyProject.beautyProject.model.entity.Product;
import com.beautyProject.beautyProject.model.entity.ProductCategory;
import com.beautyProject.beautyProject.model.entity.SkinType;
import com.beautyProject.beautyProject.repository.ProductCategoryRepository;
import com.beautyProject.beautyProject.repository.ProductRepository;
import com.beautyProject.beautyProject.repository.SkinTypeRepository;
import com.beautyProject.beautyProject.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final SkinTypeRepository skinTypeRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              SkinTypeRepository skinTypeRepository,
                              ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.skinTypeRepository = skinTypeRepository;
        this.productCategoryRepository = productCategoryRepository;
    }


    @Override
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProducts(Pageable pageable) {
        logger.info("Fetching products with pagination: {}", pageable);
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        logger.info("Creating new product: {}", product.getName());
        if (productRepository.findByName(product.getName()) != null) {
            logger.error("Product with name '{}' already exists", product.getName());
            throw new IllegalStateException("Product with name " + product.getName() + " already exists");
        }
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        logger.info("Fetching product by ID: {}", productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ResourceNotFoundException("Product not found with ID: " + productId);
                });
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {
        logger.info("Updating product with ID: {}", product.getId());
        if (!productRepository.existsById(product.getId())) {
            logger.error("Product not found with ID: {}", product.getId());
            throw new ResourceNotFoundException("Product not found with ID: " + product.getId());
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        logger.info("Deleting product with ID: {}", productId);
        if (!productRepository.existsById(productId)) {
            logger.error("Product not found with ID: {}", productId);
            throw new ResourceNotFoundException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public Product getProductByName(String name) {
        logger.info("Fetching product by name: {}", name);
        Product product = productRepository.findByName(name);
        if (product == null) {
            logger.error("Product not found with name: {}", name);
            throw new ResourceNotFoundException("Product not found with name: " + name);
        }
        return product;
    }

    @Override
    public List<Product> getProductsBySkinType(Long skinTypeId) {
        logger.info("Fetching products by skin type ID: {}", skinTypeId);
        SkinType skinType = skinTypeRepository.findById(skinTypeId)
                .orElseThrow(() -> {
                    logger.error("Skin type not found with ID: {}", skinTypeId);
                    return new ResourceNotFoundException("Skin type not found with ID: " + skinTypeId);
                });
        return productRepository.findBySuitableSkinTypesContaining(skinType);
    }

    @Override
    public List<Product> getProductsByCategory(ProductCategory category) {
        logger.info("Fetching products by category: {}", category.getName());
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        logger.info("Fetching products by category name: {}", categoryName);
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        logger.info("Fetching products by category ID: {}", categoryId);
        ProductCategory category = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", categoryId);
                    return new ResourceNotFoundException("Category not found with ID: " + categoryId);
                });
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        logger.info("Searching products with keyword: {}", keyword);
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        logger.info("Fetching products in price range: {} - {}", minPrice, maxPrice);
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Product> getTopSellingProducts(int limit) {
        logger.info("Fetching top {} selling products", limit);
        return productRepository.findTopSellingProducts(PageRequest.of(0, limit));
    }
}