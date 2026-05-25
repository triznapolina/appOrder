package com.controller;

import com.RequestsDTO.OrderItemRequest;
import com.dto.OrderInfo;
import com.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Order Item Controller", description = "Эндпоинты для управления позициями заказа")
@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Operation(
            summary = "Добавление позиции в заказ",
            description = "Создает новую позицию заказа и добавляет блюдо в заказ"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Позиция заказа успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные позиции заказа"),
            @ApiResponse(responseCode = "404", description = "Заказ или блюдо не найдены")
    })
    @PostMapping
    public OrderInfo.OrderItem createItem(@RequestBody OrderItemRequest request) {
        return orderItemService.createItem(request);
    }

    @Operation(
            summary = "Обновление позиции заказа",
            description = "Обновляет количество или данные позиции заказа"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Позиция заказа успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Позиция заказа не найдена")
    })
    @PutMapping
    public OrderInfo.OrderItem updateItem(@RequestBody OrderItemRequest request) {
        return orderItemService.updateItem(request);
    }

    @Operation(
            summary = "Удаление позиции заказа",
            description = "Удаляет блюдо из заказа"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Позиция заказа успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Позиция заказа не найдена")
    })
    @DeleteMapping("delete/{orderId}")
    public void deleteItem(

            @Parameter(description = "ID заказа")
            @PathVariable("orderId") Long orderId,

            @Parameter(description = "ID блюда")
            @RequestParam Long foodId
    ) {
        orderItemService.deleteItem(orderId, foodId);
    }

    @Operation(
            summary = "Получение позиции заказа",
            description = "Возвращает информацию о позиции заказа по её идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Позиция заказа успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Позиция заказа не найдена")
    })
    @GetMapping("/{id}")
    public OrderInfo.OrderItem getItem(

            @Parameter(description = "ID позиции заказа")
            @PathVariable("id") Long orderItemId
    ) {
        return orderItemService.findById(orderItemId);
    }
}
