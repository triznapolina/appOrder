package com.services;

import com.RequestsDTO.RegisterRequest;
import com.dto.UserInfo;
import com.dto.auth.AuthResponse;
import com.dto.auth.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse login(AuthRequest request);

    ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response);

    boolean isTokenValid(String token);

    UserInfo getUserInfo(String token);


}
