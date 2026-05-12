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

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderInfo getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public OrderInfo createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @PutMapping("/{id}")
    public OrderInfo updateOrder(@PathVariable Long id, @RequestBody UpdaterOrderRequest request) {
        return orderService.updateOrder(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/by-date")
    public List<Order> getOrdersByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return orderService.getOrderByCreated(date);
    }

    @GetMapping("/client/{clientId}")
    public List<Order> getOrdersByClient(@PathVariable Long clientId) {
        return orderService.getOrderByClientId(clientId);
    }

    @GetMapping("/client/non-completed/{clientId}")
    public Order getOrderIsNotCompletedByClient(@PathVariable Long clientId) {
        return orderService.getOrderIsNotCompletedByClientId(clientId);
    }

    @GetMapping("/status/{clientId}")
    public List<Order> getOrdersByStatus(@PathVariable Long clientId, @RequestParam String status) {
        return orderService.getOrdersByStatus(clientId, status);
    }

    @PatchMapping("/{id}/ready")
    public void completeForPickUpOrder(@PathVariable Long id, @RequestParam boolean type) {
        orderService.cancelledOrder(id, type);
    }

    @PatchMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateStatusOrder(id, status);
    }

    @PatchMapping("/{id}/deleted")
    public void setIsDeleted(@PathVariable Long id, @RequestParam Boolean status) {
        orderService.updateIsDeleted(id, status);
    }

    @PatchMapping("/{id}/total")
    public OrderInfo updateTotal(@PathVariable Long id) {
        return orderService.updateTotalPriceInOrder(id);
    }

    @PostMapping("/filter")
    public Page<Order> getAllOrders(@RequestBody FilterRequest filterRequest) {
        return orderService.getAllOrders(filterRequest);
    }


    @GetMapping("/filter")
    public ResponseEntity<Page<Order>> filterOrders(
            @RequestParam(required = false) Integer filter,
            @RequestParam(required = false) String value,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orders = orderService.filterOrders(filter, value, pageable);

        return ResponseEntity.ok(orders);
    }
}
