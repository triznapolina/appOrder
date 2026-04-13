package com.services.impl;

import com.RequestsDTO.ClientRequest;
import com.dto.Client;
import com.entity.ClientEntity;
import com.exception.AlreadyExistsException;
import com.exception.ResourceNotFoundException;
import com.inHead.FilterRequest;
import com.mapper.ClientMapper;
import com.repository.ClientRepository;
import com.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    @Override
    public Client createClient(Client request) {

        if (clientRepository.existsByEmail((request.getEmail()))) {
            throw new AlreadyExistsException("User with email=" + request.getEmail() + " is already exists");
        }

        ClientEntity user = clientMapper.toEntity(request);
        user.setEmail(request.getEmail());
        user = clientRepository.save(user);
        return clientMapper.toDto(user);
    }

    @Transactional
    @Override
    public Client updateClient(ClientRequest request) {
        ClientEntity user = clientRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id="+ request.getId() + " is not found"));

        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user = clientRepository.save(user);
        return clientMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Client getClientInfo(Long clientId) {
        ClientEntity user = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id="+ clientId + " is not found"));

        return clientMapper.toDto(user);
    }

    @Transactional
    @Override
    public void deleteClientById(Long clientId) {

        ClientEntity user = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id="+ clientId + " is not found"));

        clientRepository.delete(user);
    }

    @Transactional
    @Override
    public Client activateDeactivate(Long id, boolean active) {

        clientRepository.setStatusOfActivity(id, active);

        ClientEntity user =  clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return clientMapper.toDto(user);
    }

    @Override
    public Page<Client> getAllClientsByPage(FilterRequest request) {

        return clientRepository.findAll(PageRequest.of(request.getPage(), request.getSize()))
                .map(clientMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Client getClientByEmail(String email) {

        ClientEntity user = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email="+ email + " is not found"));

        return clientMapper.toDto(user);
    }
}
