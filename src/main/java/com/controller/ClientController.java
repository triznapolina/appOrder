package com.controller;

import com.RequestsDTO.ClientRequest;
import com.dto.Client;
import com.inHead.FilterRequest;
import com.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody ClientRequest request) {
        Client updatedClient = clientService.updateClient(request);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientInfo(@PathVariable Long clientId) {
        Client client = clientService.getClientInfo(clientId);
        return ResponseEntity.ok(client);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long clientId) {
        clientService.deleteClientById(clientId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Client> activateDeactivate(@PathVariable Long id, @RequestParam boolean active) {
        Client updatedClient = clientService.activateDeactivate(id, active);
        return ResponseEntity.ok(updatedClient);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<Client>> getAllClientsByPage(@RequestParam int page, @RequestParam int size) {
        FilterRequest filterRequest = new FilterRequest(page, size);
        Page<Client> clients = clientService.getAllClientsByPage(filterRequest);
        return ResponseEntity.ok(clients);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email")
    public ResponseEntity<Client> getClientByEmail(@RequestParam String email) {
        Client client = clientService.getClientByEmail(email);
        return ResponseEntity.ok(client);
    }
}
