package com.beautyProject.beautyProject.mapper;

import com.beautyProject.beautyProject.model.dto.UserDTO;
import com.beautyProject.beautyProject.model.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}