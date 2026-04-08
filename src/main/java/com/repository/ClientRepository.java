package com.repository;

import com.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, String> {

    ClientEntity findByEmail(String email);

    boolean existsByEmail(String email);

}
