package com.services.impl;

import com.RequestsDTO.CardRequest;
import com.entity.CardEntity;
import com.entity.ClientEntity;
import com.repository.CardRepository;
import com.repository.ClientRepository;
import com.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final ClientRepository clientRepository;
    @Override
    public CardEntity create(Long clientId, CardRequest request) {

        CardEntity card = new CardEntity();
        ClientEntity client = clientRepository.findById(clientId).orElse(null);

        card.setClient(client);
        card.setNumber(request.getCardNumber());
        card.setHolder(request.getHolderName());
        card.setExpirationDate(request.getExpiry());
        card.setCvcNumber(request.getCvcNumber());

        return cardRepository.save(card);
    }


    @Override
    public List<CardEntity> findAllByClient(Long clientId) {
        return cardRepository.findAllByClientId(clientId);
    }

    @Override
    public CardEntity findById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }
}
