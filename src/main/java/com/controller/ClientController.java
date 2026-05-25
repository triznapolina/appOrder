package com.controller;

import com.RequestsDTO.ClientRequest;
import com.dto.Client;
import com.inHead.FilterRequest;
import com.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Client Controller", description = "Эндпоинты для управления клиентами системы")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(
            summary = "Обновление данных клиента",
            description = "Обновляет информацию о клиенте"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные клиента успешно обновлены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody ClientRequest request) {
        Client updatedClient = clientService.updateClient(request);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(
            summary = "Получение информации о клиенте",
            description = "Возвращает полную информацию о клиенте по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о клиенте успешно получена"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientInfo(

            @Parameter(description = "ID клиента")
            @PathVariable Long clientId
    ) {
        Client client = clientService.getClientInfo(clientId);
        return ResponseEntity.ok(client);
    }

    @Operation(
            summary = "Получение адреса клиента",
            description = "Возвращает адрес клиента по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Адрес клиента успешно получен"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @GetMapping("/address/{clientId}")
    public ResponseEntity<String> getClientAddress(

            @Parameter(description = "ID клиента")
            @PathVariable Long clientId
    ) {
        return ResponseEntity.ok(clientService.getClientAddress(clientId));
    }

    @Operation(
            summary = "Удаление клиента",
            description = "Удаляет клиента по идентификатору. Доступно только ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Клиент успешно удален"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClientById(

            @Parameter(description = "ID клиента")
            @PathVariable Long clientId
    ) {
        clientService.deleteClientById(clientId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Активация/деактивация клиента",
            description = "Изменяет статус активности клиента. Доступно только ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус клиента успешно изменен"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Client> activateDeactivate(

            @Parameter(description = "ID клиента")
            @PathVariable Long id,

            @Parameter(description = "Статус активности клиента")
            @RequestParam boolean active
    ) {
        Client updatedClient = clientService.activateDeactivate(id, active);
        return ResponseEntity.ok(updatedClient);
    }

    @Operation(
            summary = "Получение списка клиентов",
            description = "Возвращает список всех клиентов с поддержкой пагинации. Доступно только ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список клиентов успешно получен"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<Client>> getAllClientsByPage(

            @Parameter(description = "Номер страницы")
            @RequestParam int page,

            @Parameter(description = "Количество элементов на странице")
            @RequestParam int size
    ) {
        FilterRequest filterRequest = new FilterRequest(page, size);
        Page<Client> clients = clientService.getAllClientsByPage(filterRequest);
        return ResponseEntity.ok(clients);
    }

    @Operation(
            summary = "Поиск клиента по email",
            description = "Возвращает клиента по адресу электронной почты. Доступно только ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент успешно найден"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав доступа"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email")
    public ResponseEntity<Client> getClientByEmail(

            @Parameter(description = "Email клиента")
            @RequestParam String email
    ) {
        Client client = clientService.getClientByEmail(email);
        return ResponseEntity.ok(client);
    }
}