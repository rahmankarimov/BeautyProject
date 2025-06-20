package com.beautyProject.beautyProject.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private SkinTypeDTO skinType;
    private LocalDateTime createdAt;
}