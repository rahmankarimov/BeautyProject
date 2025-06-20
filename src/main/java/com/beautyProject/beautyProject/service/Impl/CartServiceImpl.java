package com.beautyProject.beautyProject.service.Impl;



import com.beautyProject.beautyProject.exception.ResourceNotFoundException;
import com.beautyProject.beautyProject.mapper.CartMapper;
import com.beautyProject.beautyProject.model.dto.CartDTO;
import com.beautyProject.beautyProject.model.entity.Cart;
import com.beautyProject.beautyProject.model.entity.User;
import com.beautyProject.beautyProject.repository.CartRepository;
import com.beautyProject.beautyProject.repository.UserRepository;
import com.beautyProject.beautyProject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user ID: " + userId));
        return cartMapper.toCartDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO createCart(CartDTO cartDTO) {
        User user = userRepository.findById(cartDTO.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + cartDTO.getUser().getId()));


        if (cartRepository.findByUserId(user.getId()).isPresent()) {
            throw new IllegalStateException("User already has a cart");
        }

        Cart cart = cartMapper.toCart(cartDTO);
        cart.setUser(user);
        Cart savedCart = cartRepository.save(cart);

        return cartMapper.toCartDTO(savedCart);
    }

    @Override
    @Transactional
    public void deleteCart(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cart not found with ID: " + id);
        }
        cartRepository.deleteById(id);
    }
}
