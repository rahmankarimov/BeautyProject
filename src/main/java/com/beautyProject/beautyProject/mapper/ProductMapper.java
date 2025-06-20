package com.beautyProject.beautyProject.mapper;

import com.beautyProject.beautyProject.model.dto.ProductDTO;
import com.beautyProject.beautyProject.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}