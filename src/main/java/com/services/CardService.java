package com.services;

import com.RequestsDTO.CardRequest;
import com.entity.CardEntity;

import java.util.List;

public interface CardService {

    CardEntity create(Long clientId, CardRequest request);

    List<CardEntity> findAllByClient(Long clientId);

    CardEntity findById(Long id);

    void deleteById(Long id);
}
