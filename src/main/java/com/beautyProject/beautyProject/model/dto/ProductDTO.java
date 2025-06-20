package com.beautyProject.beautyProject.model.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stockQuantity;
    private ProductCategoryDTO category;
    private Set<SkinTypeDTO> suitableSkinTypes;
}