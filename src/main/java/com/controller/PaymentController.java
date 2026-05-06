package com.controller;

import com.entity.PaymentEntity;
import com.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/approve")
    public ResponseEntity<PaymentEntity> approvePayment(@RequestParam Long cardId, @RequestParam Long orderId) {
        return ResponseEntity.ok(paymentService.approvePayment(cardId, orderId));
    }
}
