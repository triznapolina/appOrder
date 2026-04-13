package com.repository;

import com.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, String> {

    Optional<ClientEntity> findByEmail(String email);

    Optional<ClientEntity> findById(Long clientId);

    boolean existsByEmail(String email);

    @Modifying
    @Query("update ClientEntity u set u.status = :active where u.id = :userId")
    void setStatusOfActivity(@Param("userId") Long userId, @Param("active") boolean active);
}
