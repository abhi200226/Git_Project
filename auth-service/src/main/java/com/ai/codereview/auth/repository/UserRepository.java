package com.ai.codereview.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.codereview.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
