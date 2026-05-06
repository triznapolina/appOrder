package com.controller;

import com.RequestsDTO.OrderItemRequest;
import com.dto.OrderInfo;
import com.services.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    // 🔹 Создать item (добавить блюдо в заказ)
    @PostMapping
    public OrderInfo.OrderItem createItem(@RequestBody OrderItemRequest request) {
        return orderItemService.createItem(request);
    }

    // 🔹 Обновить item (например количество)
    @PutMapping
    public OrderInfo.OrderItem updateItem(
            @RequestBody OrderItemRequest request
    ) {
        return orderItemService.updateItem(request);
    }

    // 🔹 Удалить item из заказа
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") Long orderItemId) {
        orderItemService.deleteItem(orderItemId);
    }

    // 🔹 Получить item по id
    @GetMapping("/{id}")
    public OrderInfo.OrderItem getItem(@PathVariable("id") Long orderItemId) {
        return orderItemService.findById(orderItemId);
    }
}
