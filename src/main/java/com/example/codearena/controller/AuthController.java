package com.example.codearena.controller;


import com.example.codearena.entity.Role;
import com.example.codearena.entity.User;
import com.example.codearena.repository.UserRepository;
import com.example.codearena.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        System.out.println("REGISTER HIT");

        // 1. Hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 2. Set default values that aren't in the JSON payload
        user.setRole(Role.USER);
        user.setRating(0);                              // 🔥 Add this to prevent 'rating cannot be null'
        user.setCreatedAt(LocalDateTime.now());         // 🔥 Add this to fix the 'created_at' error

        // 3. Save to database
        userRepository.save(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existingUser =
                userRepository.findByUsername(user.getUsername())
                        .orElseThrow();

        if (!passwordEncoder.matches(
                user.getPassword(),
                existingUser.getPassword())) {

            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(
                existingUser.getUsername()
        );
    }
}
