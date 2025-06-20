package com.beautyProject.beautyProject.mapper;



import com.beautyProject.beautyProject.model.dto.CartDTO;
import com.beautyProject.beautyProject.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDTO toCartDTO(Cart cart);

    Cart toCart(CartDTO cartDTO);

    List<CartDTO> toCartDTOList(List<Cart> cartList);

}
