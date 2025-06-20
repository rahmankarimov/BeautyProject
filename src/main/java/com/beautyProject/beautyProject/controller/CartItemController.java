package com.beautyProject.beautyProject.controller;

import com.beautyProject.beautyProject.model.entity.CartItem;
import com.beautyProject.beautyProject.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.getCartItemsByCartId(cartId));
    }

    @PostMapping
    public ResponseEntity<CartItem> addItemToCart(@RequestParam Long cartId,
                                                  @RequestParam Long productId,
                                                  @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartItemService.addItemToCart(cartId, productId, quantity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable Long id,
                                                           @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartItemService.updateCartItemQuantity(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long id) {
        cartItemService.removeItemFromCart(id);
        return ResponseEntity.ok("Item removed successfully");
    }
}