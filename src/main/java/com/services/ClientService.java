package com.services;

import com.RequestsDTO.ClientRequest;
import com.dto.Client;
import com.inHead.FilterRequest;
import org.springframework.data.domain.Page;

public interface ClientService {

    Client createClient(Client request);

    Client updateClient(ClientRequest request);

    Client getClientInfo(Long clientId);

    void deleteClientById(Long clientId);

    Client activateDeactivate(Long id, boolean active);

    Page<Client> getAllClientsByPage(FilterRequest request);

    Client getClientByEmail(String email);
}
