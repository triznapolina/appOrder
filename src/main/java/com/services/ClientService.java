package com.services;

import com.RequestsDTO.ClientRequest;
import com.dto.Client;
import org.springframework.data.domain.Page;

public interface ClientService {

    Client createClient(ClientRequest request);

    Client updateClient(ClientRequest request);

    Client getClientInfo(Long clientId);

    void deleteClientById(Long clientId);

    Client activateDeactivate(Long id, boolean active);

    Page<Client> getAllClientsByPage(int pageNo, int pageSize);

    Client getClientByEmail(String email);
}
