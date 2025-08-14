package org.example.apporders.repositories;

import org.example.apporders.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Client findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);


}
