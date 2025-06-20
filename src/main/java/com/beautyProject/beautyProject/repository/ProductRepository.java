package com.beautyProject.beautyProject.repository;

import com.beautyProject.beautyProject.model.entity.Product;
import com.beautyProject.beautyProject.model.entity.ProductCategory;
import com.beautyProject.beautyProject.model.entity.SkinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findBySuitableSkinTypesContaining(SkinType skinType);

    // ProductCategory tipindən istifadə edərək findByCategory
    List<Product> findByCategory(ProductCategory category);

    // Kateqoriya adı ilə axtarış üçün xüsusi sorğu
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    List<Product> findByNameContainingIgnoreCase(String searchTerm);

    List<Product> findByPriceLessThanEqual(Double maxPrice);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    Page<Product> findByCategory(ProductCategory category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.stock > 0 ORDER BY p.soldCount DESC")
    List<Product> findTopSellingProducts(Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.suitableSkinTypes s WHERE s.id = :skinTypeId")
    List<Product> findBySuitableSkinTypesContaining(@Param("skinTypeId") Long skinTypeId);
}