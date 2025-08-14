package org.example.apporders.repositories;

import org.example.apporders.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class RepositoryTest {

    @Autowired
    private ClientRepository  clientRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestrauntsRepository  restrauntsRepository;


    @Test
    void findClientByUsername() {

        String findName = "polina";
        Client foundByName = clientRepository.findByUsername(findName);
        assertNotNull(foundByName, "not null");
    }


    @Test
    void findFoodByName() {

        String findFood = "Burger";
        Food foundByName = foodRepository.findFoodByName(findFood);
        assertNotNull(foundByName, "not null");

    }

    @Test
    void findFoodsByCategoryId() {

        List<Food> found = foodRepository.findByCategoryId(1L);
        assertNotNull(found, "not null");

    }


    @Test
    void findFoodByPriceBetween() {

        List<Food> found = foodRepository.findFoodByPrice(BigDecimal.valueOf(10.0), BigDecimal.valueOf(55.0));
        assertNotNull(found, "not null");
    }


    @Test
    void findOrderByClientId() {

        List<Order> found = orderRepository.findByClientId(12L);
        assertNotNull(found, "not null");

    }



    @Test
    void findRestrauntByAddress() {
        Restaurant found = restrauntsRepository.findByAddress("Test Address");
        assertNotNull(found, "not null");
    }


    @Test
    void getRestaurantById() {
        Restaurant found = restrauntsRepository.getRestaurantById(9L);
        assertNotNull(found, "not null");
    }


}
