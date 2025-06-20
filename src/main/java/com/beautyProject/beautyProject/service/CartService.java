package com.beautyProject.beautyProject.service;


import com.beautyProject.beautyProject.model.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CartService {
    CartDTO getCartByUserId(Long userId);
    CartDTO createCart(CartDTO cartDTO);
    void deleteCart(Long id);
}
