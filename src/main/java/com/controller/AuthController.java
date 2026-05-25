package com.controller;

import com.RequestsDTO.RegisterRequest;
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
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth Controller", description = "Эндпоинты для аутентификации, регистрации и работы с JWT токенами")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;
    private final JwtService jwtService;
    private final ClientService userService;

    @Operation(
            summary = "Регистрация пользователя",
            description = "Создает нового пользователя в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Выполняет вход пользователя и возвращает access/refresh токены"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация"),
            @ApiResponse(responseCode = "401", description = "Неверный логин или пароль")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @Operation(
            summary = "Обновление access token",
            description = "Обновляет access token с помощью refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен успешно обновлен"),
            @ApiResponse(responseCode = "401", description = "Недействительный refresh token")
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.refreshToken(request, response);
    }

    @Operation(
            summary = "Проверка access token",
            description = "Проверяет валидность JWT access token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат проверки токена")
    })
    @GetMapping("/validate-access-token")
    public ResponseEntity<Boolean> validateToken(
            @Parameter(description = "JWT access token")
            @RequestParam String token
    ) {
        return ResponseEntity.ok(authenticationService.isTokenValid(token));
    }

    @Operation(
            summary = "Получение нового access token",
            description = "Генерирует новый access token на основе refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый токен успешно создан"),
            @ApiResponse(responseCode = "401", description = "Refresh token отсутствует или недействителен")
    })
    @PostMapping("/token")
    public ResponseEntity<AuthResponse> getNewToken(@RequestBody TokenRequest request) {

        if (request == null || request.getRefreshToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String refreshToken = request.getRefreshToken();

        if (!jwtService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final Claims claims = jwtService.getRefreshClaims(refreshToken);

        final String login = claims.getSubject();

        var user = userService.userDetailsService().loadUserByUsername(login);

        final String newAccessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(refreshToken)
                        .build()
        );
    }

    @Operation(
            summary = "Получение информации о пользователе",
            description = "Возвращает информацию о пользователе по JWT токену"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация успешно получена"),
            @ApiResponse(responseCode = "401", description = "Недействительный токен")
    })
    @GetMapping("/user-info")
    public ResponseEntity<UserInfo> getUserInfo(
            @Parameter(description = "JWT token пользователя")
            @RequestParam String token
    ) {
        return ResponseEntity.ok(authenticationService.getUserInfo(token));
    }

    @Operation(
            summary = "Получение роли пользователя",
            description = "Извлекает роль пользователя из JWT токена"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Роль успешно получена")
    })
    @GetMapping("/role")
    public ResponseEntity<String> extractRole(
            @Parameter(description = "JWT token пользователя")
            @RequestParam String token
    ) {
        return ResponseEntity.ok(jwtService.extractRole(token));
    }

    @Operation(
            summary = "Получение ID пользователя",
            description = "Извлекает идентификатор пользователя из JWT токена"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ID успешно получен")
    })
    @GetMapping("client/id")
    public ResponseEntity<Long> extractId(
            @Parameter(description = "JWT token пользователя")
            @RequestParam String token
    ) {
        return ResponseEntity.ok(jwtService.extractId(token));
    }
}
