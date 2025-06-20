package com.beautyProject.beautyProject.service.Impl;


import com.beautyProject.beautyProject.exception.ResourceNotFoundException;
import com.beautyProject.beautyProject.model.entity.User;
import com.beautyProject.beautyProject.repository.UserRepository;
import com.beautyProject.beautyProject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    @Transactional
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Email " + user.getEmail() + " already exists");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}