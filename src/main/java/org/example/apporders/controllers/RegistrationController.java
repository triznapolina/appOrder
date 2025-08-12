package org.example.apporders.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.example.apporders.exception.AlreadyExistsException;
import org.example.apporders.models.Client;
import org.example.apporders.models.RequestsDTO.RegistrationRequestDTO;
import org.example.apporders.repositories.ClientRepository;
import org.example.apporders.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/app/register")
@Tag(name = "Регистрация")
public class RegistrationController {

    @Autowired
    public ClientService clientService;

    @Autowired
    public ClientRepository clientRepository;


    @Operation(summary = "Зарегистрироваться", description = "Регистрация")
    @ApiResponse(responseCode = "200",description = "Успешный запрос",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class)))
    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequestDTO registerRequest) {
        try {
            Client newUser = clientService.registerUser(registerRequest);

            Map<String, Object> response = new HashMap<>();
            response.put("username", newUser.getUsername());
            response.put("phone", newUser.getPhone());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Получить список всех клиентов", description = "Вспомогательный функционал")
    @ApiResponse(responseCode = "200",description = "Успешный запрос",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Client.class)))
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}
