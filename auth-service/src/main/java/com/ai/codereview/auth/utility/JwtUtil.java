package com.ai.codereview.auth.utility;

import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long expiration;

  public String generateToken(Long userId, String email) {
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
    return Jwts.builder()
        .setSubject(email)
        .claim("userId", userId)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims validateToken(String token) {
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
