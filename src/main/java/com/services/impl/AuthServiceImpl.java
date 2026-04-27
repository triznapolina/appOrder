package com.services.impl;

import com.RequestsDTO.RegisterRequest;
import com.dto.UserInfo;
import com.dto.auth.AuthResponse;
import com.dto.auth.AuthRequest;
import com.entity.ClientEntity;
import com.mapper.ClientMapper;
import com.services.AuthService;
import com.services.ClientService;
import com.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegisterRequest request) {

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFullName(request.getFullName());
        clientEntity.setEmail(request.getEmail());
        clientEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        clientEntity.setRole(ClientEntity.Role.ROLE_USER);

        clientService.createClient(clientEntity);
    }


    @Override
    public AuthResponse login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = clientService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(request.getEmail(), accessToken, refreshToken);
    }


    @Override
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (jwtService.validateRefreshToken(token)) {

            String accessToken = jwtService.generateAccessToken(clientService.getOne(email));
            String refreshToken = jwtService.generateRefreshToken(clientService.getOne(email));

            return new ResponseEntity<>(new AuthResponse(email, accessToken, refreshToken), HttpStatus.OK);

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @Override
    public boolean isTokenValid(String token) {
        return jwtService.validateRefreshToken(token);
    }

    @Override
    public UserInfo getUserInfo(String token) {
        return clientMapper.toAllInfo(clientService.getOne(jwtService.extractEmail(token)));
    }
}
