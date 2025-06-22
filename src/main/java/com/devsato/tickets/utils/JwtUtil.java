package com.devsato.tickets.utils;

import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;

public final class JwtUtil {
  private JwtUtil() {
  }

  public static UUID parseUserId(Jwt jwt) {
    UUID userId = UUID.fromString(jwt.getSubject());
    return userId;
  }
}
