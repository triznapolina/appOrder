package com.controller;

import com.RequestsDTO.OrderRequest;
import com.RequestsDTO.UpdaterOrderRequest;
import com.dto.Order;
import com.dto.OrderInfo;
import com.inHead.FilterRequest;
import com.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Order Controller", description = "Эндпоинты для управления заказами")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Получение заказа по ID",
            description = "Возвращает полную информацию о заказе"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ успешно найден"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @GetMapping("/{id}")
    public OrderInfo getOrder(

            @Parameter(description = "ID заказа")
            @PathVariable Long id
    ) {
        return orderService.getOrder(id);
    }

    @Operation(
            summary = "Создание заказа",
            description = "Создает новый заказ"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные заказа")
    })
    @PostMapping
    public OrderInfo createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @Operation(
            summary = "Обновление заказа",
            description = "Обновляет информацию о заказе"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @PutMapping("/{id}")
    public OrderInfo updateOrder(

            @Parameter(description = "ID заказа")
            @PathVariable Long id,

            @RequestBody UpdaterOrderRequest request
    ) {
        return orderService.updateOrder(id, request);
    }

    @Operation(
            summary = "Удаление заказа",
            description = "Удаляет заказ по идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ успешно удален"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @DeleteMapping("/{id}")
    public void deleteOrder(

            @Parameter(description = "ID заказа")
            @PathVariable Long id
    ) {
        orderService.deleteOrder(id);
    }

    @Operation(
            summary = "Получение заказов по дате",
            description = "Возвращает список заказов, созданных в указанную дату"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов успешно получен")
    })
    @GetMapping("/by-date")
    public List<Order> getOrdersByDate(

            @Parameter(description = "Дата создания заказа")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return orderService.getOrderByCreated(date);
    }

    @Operation(
            summary = "Получение заказов клиента",
            description = "Возвращает список заказов определенного клиента"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов успешно получен"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @GetMapping("/client/{clientId}")
    public List<Order> getOrdersByClient(

            @Parameter(description = "ID клиента")
            @PathVariable Long clientId
    ) {
        return orderService.getOrderByClientId(clientId);
    }

    @Operation(
            summary = "Получение незавершенного заказа клиента",
            description = "Возвращает текущий незавершенный заказ клиента"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ успешно найден"),
            @ApiResponse(responseCode = "404", description = "Незавершенный заказ не найден")
    })
    @GetMapping("/client/non-completed/{clientId}")
    public Order getOrderIsNotCompletedByClient(

            @Parameter(description = "ID клиента")
            @PathVariable Long clientId
    ) {
        return orderService.getOrderIsNotCompletedByClientId(clientId);
    }

    @Operation(
            summary = "Получение заказов по статусу",
            description = "Возвращает список заказов клиента по указанному статусу"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов успешно получен")
    })
    @GetMapping("/status/{clientId}")
    public List<Order> getOrdersByStatus(

            @Parameter(description = "ID клиента")
            @PathVariable Long clientId,

            @Parameter(description = "Статус заказа")
            @RequestParam String status
    ) {
        return orderService.getOrdersByStatus(clientId, status);
    }

    @Operation(
            summary = "Изменение статуса готовности заказа",
            description = "Отмечает заказ как готовый к выдаче или отмененный"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус заказа успешно изменен"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @PatchMapping("/{id}/ready")
    public void completeForPickUpOrder(

            @Parameter(description = "ID заказа")
            @PathVariable Long id,

            @Parameter(description = "Статус готовности")
            @RequestParam boolean type
    ) {
        orderService.cancelledOrder(id, type);
    }

    @Operation(
            summary = "Обновление статуса заказа",
            description = "Изменяет статус заказа"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус заказа успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @PatchMapping("/{id}/status")
    public Order updateStatus(

            @Parameter(description = "ID заказа")
            @PathVariable Long id,

            @Parameter(description = "Новый статус заказа")
            @RequestParam String status
    ) {
        return orderService.updateStatusOrder(id, status);
    }

    @Operation(
            summary = "Изменение статуса удаления заказа",
            description = "Изменяет флаг удаления заказа"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус удаления успешно изменен"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @PatchMapping("/{id}/deleted")
    public void setIsDeleted(

            @Parameter(description = "ID заказа")
            @PathVariable Long id,

            @Parameter(description = "Статус удаления")
            @RequestParam Boolean status
    ) {
        orderService.updateIsDeleted(id, status);
    }

    @Operation(
            summary = "Обновление итоговой стоимости заказа",
            description = "Пересчитывает и обновляет итоговую стоимость заказа"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Стоимость заказа успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @PatchMapping("/{id}/total")
    public OrderInfo updateTotal(

            @Parameter(description = "ID заказа")
            @PathVariable Long id
    ) {
        return orderService.updateTotalPriceInOrder(id);
    }

    @Operation(
            summary = "Получение всех заказов",
            description = "Возвращает список заказов с фильтрацией и пагинацией"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов успешно получен")
    })
    @PostMapping("/filter")
    public Page<Order> getAllOrders(@RequestBody FilterRequest filterRequest) {
        return orderService.getAllOrders(filterRequest);
    }

    @Operation(
            summary = "Фильтрация заказов",
            description = "Возвращает список заказов по заданным параметрам фильтрации"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список заказов успешно получен")
    })
    @GetMapping("/filter")
    public ResponseEntity<Page<Order>> filterOrders(

            @Parameter(description = "Тип фильтра")
            @RequestParam(required = false) Integer filter,

            @Parameter(description = "Значение фильтра")
            @RequestParam(required = false) String value,

            @Parameter(description = "Номер страницы")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Количество элементов на странице")
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orders = orderService.filterOrders(filter, value, pageable);

        return ResponseEntity.ok(orders);
    }
}
