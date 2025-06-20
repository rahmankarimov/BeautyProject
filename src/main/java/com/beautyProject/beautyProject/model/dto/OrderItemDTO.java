package com.beautyProject.beautyProject.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private ProductDTO product;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
}