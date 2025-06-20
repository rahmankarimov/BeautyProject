package com.beautyProject.beautyProject.model.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
public class OrderDTO {
    private Long id;
    private UserDTO user;
    private BigDecimal totalAmount;
    private String status;
    private String shippingAddress;
    private Map<String, Object> paymentInfo;
    private LocalDateTime createdAt;
    private Set<OrderItemDTO> items;
}