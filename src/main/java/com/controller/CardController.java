package com.controller;

import com.RequestsDTO.CardRequest;
import com.entity.CardEntity;
import com.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Card Controller", description = "Эндпоинты для управления банковскими картами пользователей")
@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @Operation(
            summary = "Создание банковской карты",
            description = "Создает новую банковскую карту для указанного клиента"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Карта успешно создана"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные карты")
    })
    @PostMapping("{clientId}")
    public ResponseEntity<CardEntity> create(
            @Parameter(description = "ID клиента")
            @PathVariable Long clientId,

            @RequestBody CardRequest request
    ) {
        CardEntity card = cardService.create(clientId, request);
        return ResponseEntity.ok(card);
    }

    @Operation(
            summary = "Получение карт клиента",
            description = "Возвращает список всех банковских карт, принадлежащих клиенту"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список карт успешно получен"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CardEntity>> getByClient(
            @Parameter(description = "ID клиента")
            @PathVariable Long clientId
    ) {
        List<CardEntity> cards = cardService.findAllByClient(clientId);
        return ResponseEntity.ok(cards);
    }

    @Operation(
            summary = "Получение карты по ID",
            description = "Возвращает информацию о банковской карте по её идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Карта успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CardEntity> getById(
            @Parameter(description = "ID карты")
            @PathVariable Long id
    ) {
        CardEntity card = cardService.findById(id);
        return ResponseEntity.ok(card);
    }

    @Operation(
            summary = "Удаление карты",
            description = "Удаляет банковскую карту по её идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Карта успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID карты")
            @PathVariable Long id
    ) {
        cardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
