package com.controller;

import com.entity.PaymentEntity;
import com.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Payment Controller", description = "Эндпоинты для управления платежами и оплатой заказов")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
            summary = "Подтверждение оплаты заказа",
            description = "Выполняет оплату заказа с использованием банковской карты"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Оплата успешно выполнена"),
            @ApiResponse(responseCode = "400", description = "Недостаточно средств или некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Карта или заказ не найдены")
    })
    @PostMapping("/approve")
    public ResponseEntity<PaymentEntity> approvePayment(

            @Parameter(description = "ID банковской карты")
            @RequestParam Long cardId,

            @Parameter(description = "ID заказа")
            @RequestParam Long orderId
    ) {
        return ResponseEntity.ok(
                paymentService.approvePayment(cardId, orderId)
        );
    }

    @Operation(
            summary = "Получение платежа по ID заказа",
            description = "Возвращает информацию о платеже, связанном с заказом"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Платеж успешно найден"),
            @ApiResponse(responseCode = "404", description = "Платеж или заказ не найдены")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentEntity> getPaymentByOrderId(

            @Parameter(description = "ID заказа")
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(
                paymentService.getPaymentByOrderId(orderId)
        );
    }

    @Operation(
            summary = "Получение платежа по ID",
            description = "Возвращает информацию о платеже по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Платеж успешно найден"),
            @ApiResponse(responseCode = "404", description = "Платеж не найден")
    })
    @GetMapping("by/{id}")
    public ResponseEntity<PaymentEntity> getPaymentById(

            @Parameter(description = "ID платежа")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                paymentService.getPaymentById(id)
        );
    }
}
