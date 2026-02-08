package com.ai.codereview.auth.utility;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  private final String SECRET_KEY = "secret-key-very-secure";
  private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

  public String generateToken(Long userId, String email) {
    return Jwts.builder()
        .setSubject(email)
        .claim("userId", userId)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims validateToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY.getBytes())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
