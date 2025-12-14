package com.vlsi.chatbot.controller;

import com.vlsi.chatbot.dto.ApiResponse;
import com.vlsi.chatbot.dto.RegisterRequest;
import com.vlsi.chatbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {
            // Validate input
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Username is required"));
            }
            
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Password is required"));
            }
            
            if (request.getUsername().trim().length() < 3) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Username must be at least 3 characters"));
            }
            
            if (request.getPassword().length() < 4) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Password must be at least 4 characters"));
            }
            
            // Check if user exists
            if (userService.existsByUsername(request.getUsername().trim())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Username already exists"));
            }
            
            // Register user
            userService.registerUser(request.getUsername().trim(), request.getPassword());
            
            return ResponseEntity.ok(ApiResponse.success("Registration successful!"));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Registration failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/whoami")
    public ResponseEntity<ApiResponse> whoami(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return ResponseEntity.ok(ApiResponse.success("Authenticated", userDetails.getUsername()));
        }
        return ResponseEntity.ok(ApiResponse.error("Not authenticated"));
    }
}
