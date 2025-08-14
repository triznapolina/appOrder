package org.example.apporders.security;


import org.example.apporders.exception.ResourceNotFoundException;
import org.example.apporders.models.Client;
import org.example.apporders.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class ClientDetailService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        Client client = clientRepository.findByUsername(username);

        if(client == null) {
            throw new ResourceNotFoundException("Client not found");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("active"));

        return new User(client.getUsername(), client.getPassword(), authorities);
    }

}
