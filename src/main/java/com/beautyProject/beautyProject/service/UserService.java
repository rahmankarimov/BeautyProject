package com.beautyProject.beautyProject.service;


import com.beautyProject.beautyProject.model.entity.User;

import java.util.List;


import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
}
