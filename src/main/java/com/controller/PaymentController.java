package com.controller;

import com.entity.PaymentEntity;
import com.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/approve")
    public ResponseEntity<PaymentEntity> approvePayment(@RequestParam Long cardId, @RequestParam Long orderId) {
        return ResponseEntity.ok(paymentService.approvePayment(cardId, orderId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentEntity> getPaymentByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    @GetMapping("by/{id}")
    public ResponseEntity<PaymentEntity> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }
}
