package com.services.impl;

import com.RequestsDTO.ClientRequest;
import com.dto.Client;
import com.mapper.ClientMapper;
import com.repository.ClientRepository;
import com.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client createClient(ClientRequest request) {
        return null;
    }

    @Override
    public Client updateClient(ClientRequest request) {
        return null;
    }

    @Override
    public Client getClientInfo(Long clientId) {
        return null;
    }

    @Override
    public void deleteClientById(Long clientId) {

    }

    @Override
    public Client activateDeactivate(Long id, boolean active) {
        return null;
    }

    @Override
    public Page<Client> getAllClientsByPage(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public Client getClientByEmail(String email) {
        return null;
    }
}
