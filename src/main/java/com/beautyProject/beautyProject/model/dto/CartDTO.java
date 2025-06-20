package com.beautyProject.beautyProject.model.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CartDTO {
    private Long id;
    private UserDTO user;
    private Set<CartItemDTO> items;
    private LocalDateTime createdAt;
}