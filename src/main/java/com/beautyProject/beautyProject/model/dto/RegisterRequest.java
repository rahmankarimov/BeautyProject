package com.beautyProject.beautyProject.model.dto;

import com.beautyProject.beautyProject.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role = Role.ROLE_USER; // Default is USER if not specified
}