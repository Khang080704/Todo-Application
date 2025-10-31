package com.example.todoapplication.controller;

import com.example.todoapplication.model.LoginRequest;
import com.example.todoapplication.model.RegisterRequest;
import com.example.todoapplication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail(), registerRequest.getFirstName(), registerRequest.getLastName());
        return ResponseEntity.ok().body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        Map<String, String> result = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok().body(result);
    }

}
