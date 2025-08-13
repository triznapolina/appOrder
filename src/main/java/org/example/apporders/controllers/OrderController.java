package org.example.apporders.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.apporders.exception.ResourceNotFoundException;
import org.example.apporders.models.*;
import org.example.apporders.models.RequestsDTO.CreateOrderRequestDTO;
import org.example.apporders.services.ClientService;
import org.example.apporders.services.FoodService;
import org.example.apporders.services.OrderService;
import org.example.apporders.services.RestrauntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/app/order")
@Tag(name = "Основные возможности приложения")
public class OrderController {

    @Autowired
    private RestrauntService restrauntService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;


    @Operation(summary = "Оформить заказ", description = "Основной функционал")
    @ApiResponse(responseCode = "200",description = "Успешный запрос",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequestDTO request) {
        try {
            orderService.createOrder(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Изменить заказ", description = "Основной функционал")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Успешный запрос"),
            @ApiResponse(responseCode = "404",description = "Заказ не найден"),

    })
    @PatchMapping("/orders/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long id,
                                             @RequestBody CreateOrderRequestDTO request) {
        orderService.updateOrder(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Получить заказы пользователя", description = "Основной функционал")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Успешный запрос"),
            @ApiResponse(responseCode = "404",description = "Заказы не найдены"),

    })
    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getAllOrdersForCurrentUser() {
        List<Order> orders = orderService.getAllOrdersForCurrentUser();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @Operation(summary = "Получить конкретный заказ", description = "Основной функционал")
    @ApiResponse(responseCode = "200",description = "Успешный запрос",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @Operation(summary = "Удалить заказ", description = "Основной функционал")
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Поиск ресторана по адресу", description = "Вспомогательный функционал")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Успешный запрос"),
            @ApiResponse(responseCode = "404",description = "Ресторан не найден"),

    })
    @GetMapping("/rst-by-address")
    public ResponseEntity<Restraunt> getRestaurantByAddress(@RequestParam String address) {
            Restraunt restraunt = restrauntService.getRestrauntByAddress(address);
            return new ResponseEntity<>(restraunt, HttpStatus.OK);
    }


    @Operation(summary = "Отображение всех ресторанов", description = "Вспомогательный функционал")
    @ApiResponse(responseCode = "200",description = "Успешный запрос",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restraunt.class)))
    @GetMapping("/restraunts/all-rst")
    public ResponseEntity<List<Restraunt>> getAllRestaurants() {
        List<Restraunt> restraunts = restrauntService.getAllRestraunts();
        return new ResponseEntity<>(restraunts, HttpStatus.OK);
    }


    @Operation(summary = "Фильтрация еды по категориям", description = "Вспомогательный функционал")
    @ApiResponse(responseCode = "200",description = "Успешный запрос",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Food.class)))
    @GetMapping("/foods/filter-by-category")
    public ResponseEntity<List<Food>> getFoodsByCategory(@RequestParam Long categoryId) {
        List<Food> foods = foodService.findByCategoryId(categoryId);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


    @Operation(summary = "Поиск заказа по дате", description = "Вспомогательный функционал")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Успешный запрос"),
            @ApiResponse(responseCode = "404",description = "Заказ не найден"),

    })
    @GetMapping("/orders/by-date")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam LocalDate date) {
        List<Order> orders = orderService.getOrderbyDate(date);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }




    @Operation(summary = "Фильтрация блюд по диапозону цены", description = "Вспомогательный функционал")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Успешный запрос"),
            @ApiResponse(responseCode = "404",description = "Блюда в указанном диапозоне не найдены"),

    })
    @GetMapping("/foods/filter-by-price")
    public ResponseEntity<List<Food>> findByPriceBetween(@RequestParam BigDecimal minRange,
                                                         @RequestParam BigDecimal maxRange) {
        List<Food> foods = foodService.findByPriceBetween(minRange, maxRange);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


    @Operation(summary = "Поиск блюд по названию", description = "Вспомогательный функционал")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Успешный запрос"),
            @ApiResponse(responseCode = "404",description = "Блюдо не найдено"),

    })
    @GetMapping("/foods/food-by-name")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        try {
            Food food = foodService.findByName(name);
            if(food == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(food, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Поиск клиента по имени", description = "Вспомогательный функционал")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Успешный запрос"),
            @ApiResponse(responseCode = "404",description = "Клиент не найден"),

    })
    @GetMapping("/client-by-name")
    public ResponseEntity<Client> getUserByName(@RequestParam String name) {
        Client client = clientService.getClientByUsername(name);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }


}
