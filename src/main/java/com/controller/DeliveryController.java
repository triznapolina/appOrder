package com.controller;

import com.dto.OrderInfo;
import com.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Delivery Controller", description = "Эндпоинты для управления информацией о доставке заказов")
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(
            summary = "Создание информации о доставке",
            description = "Создает информацию о доставке для указанного заказа"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о доставке успешно создана"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные доставки")
    })
    @PostMapping("/{orderId}")
    public ResponseEntity<OrderInfo.Delivery> createDeliveryInfo(

            @Parameter(description = "ID заказа")
            @PathVariable Long orderId,

            @RequestBody OrderInfo.Delivery delivery
    ) {
        return ResponseEntity.ok(
                deliveryService.createDeliveryInfo(delivery, orderId)
        );
    }

    @Operation(
            summary = "Получение информации о доставке",
            description = "Возвращает информацию о доставке для указанного заказа"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о доставке успешно получена"),
            @ApiResponse(responseCode = "404", description = "Информация о доставке или заказ не найдены")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInfo.Delivery> getDeliveryInfoOfOrder(

            @Parameter(description = "ID заказа")
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(
                deliveryService.getDeliveryInfoOfOrder(orderId)
        );
    }
}
