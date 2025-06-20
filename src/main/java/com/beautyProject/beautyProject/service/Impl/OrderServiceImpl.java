package com.beautyProject.beautyProject.service.Impl;

import com.beautyProject.beautyProject.exception.ResourceNotFoundException;
import com.beautyProject.beautyProject.model.entity.Order;
import com.beautyProject.beautyProject.model.entity.User;
import com.beautyProject.beautyProject.repository.OrderRepository;
import com.beautyProject.beautyProject.repository.UserRepository;
import com.beautyProject.beautyProject.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + order.getUser().getId()));

        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }
}