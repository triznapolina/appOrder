package com.controller;

import com.RequestsDTO.RegisterRequest;
import com.dto.UserInfo;
import com.dto.auth.AuthRequest;
import com.dto.auth.AuthResponse;
import com.services.AuthService;
import com.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.refreshToken(request, response);
    }

    @GetMapping("/validate-access-token")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        return ResponseEntity.ok(authenticationService.isTokenValid(token));
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserInfo> getUserInfo(@RequestParam String token) {
        return ResponseEntity.ok(authenticationService.getUserInfo(token));
    }

    @GetMapping("/role")
    public ResponseEntity<String> extractRole(@RequestParam String token) {
        return ResponseEntity.ok(jwtService.extractRole(token));
    }

}
