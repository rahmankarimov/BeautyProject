package com.beautyProject.beautyProject.mapper;

import com.beautyProject.beautyProject.model.dto.OrderDTO;
import com.beautyProject.beautyProject.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);
}