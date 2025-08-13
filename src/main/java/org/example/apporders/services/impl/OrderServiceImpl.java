package org.example.apporders.services.impl;

import jakarta.transaction.Transactional;
import org.example.apporders.exception.ResourceNotFoundException;
import org.example.apporders.models.*;
import org.example.apporders.repositories.*;
import org.example.apporders.models.RequestsDTO.CreateOrderRequestDTO;
import org.example.apporders.models.RequestsDTO.MenuItemRequestDTO;
import org.example.apporders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RestrauntsRepository restrauntsRepository;

    @Autowired
    private FoodRepository foodRepository;


    @Override
    @Transactional
    public void createOrder(@RequestBody CreateOrderRequestDTO request) {

        Client client = getCurrentClient();

        Restraunt restaurant = restrauntsRepository.findById(request.getRestaurantId()).
                orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " +
                        request.getRestaurantId()));

        Order order = new Order();
        order.setDate(LocalDate.from(LocalDateTime.now()));
        order.setComment(request.getComment());
        order.setRestaurant(restaurant);
        order.setClient(client);


        Order savedOrder = orderRepository.save(order);



        BigDecimal totalPrice = BigDecimal.ZERO;


        List<OrderItem> orderItems = new ArrayList<>();
        for (MenuItemRequestDTO itemRequest : request.getMenuItems()) {
            Food food = foodRepository.findById(itemRequest.getFoodId()).
                    orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " +
                            itemRequest.getFoodId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setFood(food);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItems.add(orderItem);

            BigDecimal itemPrice = food.getPrice().multiply
                    (BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice = totalPrice.add(itemPrice);
        }

        orderItemRepository.saveAll(orderItems);

        savedOrder.setPrice(totalPrice);

    }


    private Client getCurrentClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            String username = ((User) authentication.getPrincipal()).getUsername();
            Client client = clientRepository.findByUsername(username);
            return client;
        } else {
            throw new IllegalStateException( "User not authenticated or principal is not a UserDetails instance");
        }
    }



    @Override
    @Transactional
    public void updateOrder(Long id, CreateOrderRequestDTO request) {
        Order orderToUpdate = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        Restraunt restaurant = restrauntsRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " +
                        request.getRestaurantId()));

        orderToUpdate.setRestaurant(restaurant);
        orderToUpdate.setComment(request.getComment());
        orderToUpdate.setDate(LocalDate.from(LocalDateTime.now()));

        List<OrderItem> existingOrderItems = orderItemRepository.findByOrder(orderToUpdate);
        orderItemRepository.deleteAll(existingOrderItems);

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (MenuItemRequestDTO itemRequest : request.getMenuItems()) {
            Food food = foodRepository.findById(itemRequest.getFoodId())
                    .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " +
                            itemRequest.getFoodId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orderToUpdate);
            orderItem.setFood(food);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItems.add(orderItem);

            BigDecimal itemPrice = food.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice = totalPrice.add(itemPrice);
        }

        orderItemRepository.saveAll(orderItems);
        orderToUpdate.setPrice(totalPrice);

        orderRepository.save(orderToUpdate);

    }



    @Override
    public Order getOrder(Long id) {
        return  orderRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Order not found with id: " + id));
    }


    @Override
    public List<Order> getOrderbyDate(LocalDate date) {
        return orderRepository.findOrdersByDate(date);
    }



    @Override
    public void deleteOrder(Long id) {
        Order order = getOrder(id);
        orderItemRepository.deleteAll(order.getOrderItems());
        orderRepository.delete(order);
    }

    @Override
    public List<Order> getAllOrdersForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Client client = clientRepository.findByUsername(username);
        if (client == null) {
            throw new IllegalStateException("Client not found for username: " + username);
        }
        return orderRepository.findByClientId(client.getId());
    }


}
