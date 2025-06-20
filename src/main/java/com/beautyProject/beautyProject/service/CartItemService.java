package com.beautyProject.beautyProject.service;

import com.beautyProject.beautyProject.model.entity.CartItem;
import java.util.List;

public interface CartItemService {

    List<CartItem> getCartItemsByCartId(Long cartId);
    CartItem addItemToCart(Long cartId, Long productId, Integer quantity);
    CartItem updateCartItemQuantity(Long cartItemId, Integer quantity);
    void removeItemFromCart(Long cartItemId);
}