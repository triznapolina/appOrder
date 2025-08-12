package org.example.apporders.services;

import org.example.apporders.models.RequestsDTO.CreateOrderRequestDTO;
import org.example.apporders.models.RequestsDTO.MenuItemRequestDTO;
import org.example.apporders.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@WithMockUser("polina")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;


    @Test
    void createOrder() {

        MenuItemRequestDTO itemDto1 = new MenuItemRequestDTO(28L, 2);
        MenuItemRequestDTO itemDto2 = new MenuItemRequestDTO(47L, 5);
        CreateOrderRequestDTO request = new CreateOrderRequestDTO(
                9L,
                Arrays.asList(itemDto1, itemDto2),
                "Happy Birthday"
        );

        Order order = orderService.createOrder(request);
        assertNotNull(order, "not null");


    }


    @Test
    void getOrderByID() {
        Order order = orderService.getOrder(5L);
        assertNotNull(order, "not null");
    }


    @Test
    void deleteOrder() {
        orderService.deleteOrder(6L);

    }

    @Test
    void getAllOrdersForCurrentUser() {

        List<Order> orders = orderService.getAllOrdersForCurrentUser();
        assertNotNull(orders, "not null");


    }



}

