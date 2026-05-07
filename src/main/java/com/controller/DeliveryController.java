package com.controller;

import com.dto.OrderInfo;
import com.services.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/{orderId}")
    public ResponseEntity<OrderInfo.Delivery> createDeliveryInfo(@PathVariable Long orderId, @RequestBody OrderInfo.Delivery delivery) {
        return ResponseEntity.ok(deliveryService.createDeliveryInfo(delivery, orderId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInfo.Delivery> getDeliveryInfoOfOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(deliveryService.getDeliveryInfoOfOrder(orderId));
    }
}
