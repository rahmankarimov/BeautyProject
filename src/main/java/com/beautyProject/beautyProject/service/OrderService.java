package com.beautyProject.beautyProject.service;

import com.beautyProject.beautyProject.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getAllOrders(Pageable pageable);
    List<Order> getUserOrders(Long userId);
    Order createOrder(Order order);
    void deleteOrder(Long orderId);
}