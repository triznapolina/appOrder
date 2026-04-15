package com.services;

import io.jsonwebtoken.Claims;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

public interface JwtService {

    String generateAccessToken(UserDetails user);
    String generateRefreshToken(UserDetails user);
    String generateToken(Map<String, Object> extraClaims, UserDetails user, long expiryTime);

    Map<String, Object> createClaims(UserDetails user);
    Claims extractAllClaims(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolvers);
    String extractEmail(String token);
    String extractRole(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
    boolean validateAccessToken(@NonNull String accessToken);
    boolean validateRefreshToken(@NonNull String refreshToken);

    boolean isTokenExpired(String token);
    Date extractExpiration(String token);

    Key getSigningKey();

}
