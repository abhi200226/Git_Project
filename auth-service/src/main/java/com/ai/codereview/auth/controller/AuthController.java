package com.ai.codereview.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.codereview.auth.dto.AuthResponse;
import com.ai.codereview.auth.dto.LoginRequest;
import com.ai.codereview.auth.dto.RegisterRequest;
import com.ai.codereview.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
    authService.register(request);
    return ResponseEntity.ok("User registered successfully");
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @GetMapping("/health")
  public String health() {
    return "AUTH SERVICE UP";
  }
}

