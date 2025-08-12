package org.example.apporders.services.impl;

import jakarta.validation.Valid;
import org.example.apporders.exception.AlreadyExistsException;
import org.example.apporders.models.Client;
import org.example.apporders.models.RequestsDTO.RegistrationRequestDTO;
import org.example.apporders.repositories.ClientRepository;
import org.example.apporders.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ClientServiceImpl implements ClientService {


    @Autowired
    public ClientRepository clientRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;



    @Override
    public Client registerUser(@Valid RegistrationRequestDTO request) {

        if (clientRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException("Such username '" + request.getUsername() + "' is already taken.");
        }

        if (clientRepository.existsByPhone(request.getPhone())) {
            throw new AlreadyExistsException("Such phone number '" + request.getPhone() + "' is already " +
                    "in use.");
        }

        Client newUser = new Client();
        newUser.setUsername(request.getUsername());
        newUser.setPhone(request.getPhone());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        return clientRepository.save(newUser);
    }

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

}
