package org.example.apporders.services;

import org.example.apporders.models.Client;
import org.example.apporders.models.RequestsDTO.RegistrationRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Test
    void registerUser() {
        RegistrationRequestDTO request = new RegistrationRequestDTO(
                "testuser",
                "+375295869574",
                "1111"
        );

        Client newClient = clientService.registerUser(request);
        assertNotNull(newClient, "not null");
    }


    @Test
    void getClientByUsername() {
        Client foundClient = clientService.getClientByUsername("polina");
        assertNotNull(foundClient, "not null");
    }


}
