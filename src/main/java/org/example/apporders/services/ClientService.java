package org.example.apporders.services;

import jakarta.validation.Valid;
import org.example.apporders.models.Client;
import org.example.apporders.models.RequestsDTO.RegistrationRequestDTO;


public interface ClientService {

    Client registerUser(@Valid RegistrationRequestDTO registerRequest);

    Client getClientByUsername(String username);
}
