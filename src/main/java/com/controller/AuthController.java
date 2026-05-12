package com.controller;

import com.RequestsDTO.RegisterRequest;
import com.dto.Client;
import com.dto.TokenRequest;
import com.dto.UserInfo;
import com.dto.auth.AuthRequest;
import com.dto.auth.AuthResponse;
import com.services.AuthService;
import com.services.ClientService;
import com.services.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;
    private final JwtService jwtService;
    private final ClientService userService;

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


    @PostMapping("/token")
    public ResponseEntity<AuthResponse> getNewToken(
            @RequestBody TokenRequest request
    ) {

        if (
                request == null ||
                        request.getRefreshToken() == null
        ) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        String refreshToken = request.getRefreshToken();

        if (!jwtService.validateRefreshToken(refreshToken)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        final Claims claims =
                jwtService.getRefreshClaims(refreshToken);

        final String login =
                claims.getSubject();

        var user =
                userService.userDetailsService()
                        .loadUserByUsername(login);

        final String newAccessToken =
                jwtService.generateAccessToken(user);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(refreshToken)
                        .build()
        );
    }



    @GetMapping("/user-info")
    public ResponseEntity<UserInfo> getUserInfo(@RequestParam String token) {
        return ResponseEntity.ok(authenticationService.getUserInfo(token));
    }

    @GetMapping("/role")
    public ResponseEntity<String> extractRole(@RequestParam String token) {
        return ResponseEntity.ok(jwtService.extractRole(token));
    }

    @GetMapping("client/id")
    public ResponseEntity<Long> extractId(@RequestParam String token) {
        return ResponseEntity.ok(jwtService.extractId(token));
    }

}
