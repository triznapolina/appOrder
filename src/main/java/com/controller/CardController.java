package com.controller;

import com.RequestsDTO.CardRequest;
import com.entity.CardEntity;
import com.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("{clientId}")
    public ResponseEntity<CardEntity> create(@PathVariable Long clientId, @RequestBody CardRequest request) {
        CardEntity card = cardService.create(clientId, request);
        return ResponseEntity.ok(card);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CardEntity>> getByClient(@PathVariable Long clientId) {
        List<CardEntity> cards = cardService.findAllByClient(clientId);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardEntity> getById(@PathVariable Long id) {
        CardEntity card = cardService.findById(id);
        return ResponseEntity.ok(card);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
