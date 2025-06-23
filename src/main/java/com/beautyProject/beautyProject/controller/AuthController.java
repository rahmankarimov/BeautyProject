package com.beautyProject.beautyProject.controller;

import com.beautyProject.beautyProject.enums.Role;
import com.beautyProject.beautyProject.model.dto.JwtAuthenticationResponse;
import com.beautyProject.beautyProject.model.dto.LoginRequest;
import com.beautyProject.beautyProject.model.dto.RegisterRequest;
import com.beautyProject.beautyProject.model.entity.User;
import com.beautyProject.beautyProject.repository.UserRepository;
import com.beautyProject.beautyProject.security.JwtTokenProvider;
import com.beautyProject.beautyProject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          UserService userService, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        } catch (BadCredentialsException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        Map<String, String> response = new HashMap<>();
        
        try {
            // Check if email already exists
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                response.put("error", "Email is already in use");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Create new user
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            
            // For security, always set a default role (don't allow role from request)
            // If you need admin creation, create a separate endpoint with proper security
            user.setRole(Role.ROLE_USER);
            
            // Let the service handle password encryption
            userService.createUser(user);

            response.put("message", "User registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("error", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}