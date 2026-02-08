package com.ai.codereview.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ai.codereview.auth.dto.AuthResponse;
import com.ai.codereview.auth.dto.LoginRequest;
import com.ai.codereview.auth.dto.RegisterRequest;
import com.ai.codereview.auth.entity.User;
import com.ai.codereview.auth.repository.UserRepository;
import com.ai.codereview.auth.utility.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;

  public void register(RegisterRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new RuntimeException("Email already exists");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepository.save(user);
  }

  public AuthResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = jwtUtil.generateToken(user.getId(), user.getEmail());
    return new AuthResponse(token);
  }
}
