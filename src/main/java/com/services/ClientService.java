package com.services;

import com.RequestsDTO.ClientRequest;
import com.dto.Client;
import com.entity.ClientEntity;
import com.inHead.FilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ClientService {

    void createClient(ClientEntity request);

    Client updateClient(ClientRequest request);

    Client getClientInfo(Long clientId);

    void deleteClientById(Long clientId);

    Client activateDeactivate(Long id, boolean active);

    Page<Client> getAllClientsByPage(FilterRequest request);

    Client getClientByEmail(String email);

    ClientEntity getOne(String email);

    UserDetailsService userDetailsService();
}
