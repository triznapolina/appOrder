package org.example.apporders.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apporders.models.RequestsDTO.CreateOrderRequestDTO;
import org.example.apporders.models.RequestsDTO.MenuItemRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("polina")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void findClientByUsername() throws Exception {

        mockMvc.perform(get("/app/order/client-by-name")
                        .param("name", "polina")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    void findFoodByName() throws Exception {

        mockMvc.perform(get("/app/order/foods/food-by-name")
                        .param("name", "Burger")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void findFoodByNameNegative() throws Exception {

        mockMvc.perform(get("/app/order/foods/food-by-name")
                        .param("name", "Home")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void findByPriceBetween_ReturnsListOfFoods() throws Exception {

        mockMvc.perform(get("/app/order/foods/filter-by-price")
                        .param("minRange", "10.0")
                        .param("maxRange", "35.2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getFoodsByCategory() throws Exception {
        mockMvc.perform(get("/app/order/foods/filter-by-category")
                        .param("categoryId", "4"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllRestaurants() throws Exception {
        mockMvc.perform(get("/app/order/restraunts/all-rst"))
                .andExpect(status().isOk());
    }

    @Test
    void getRestaurantByAddress() throws Exception {
        mockMvc.perform(get("/app/order/rst-by-address")
                        .param("address", "Test Address"))
                .andExpect(status().isOk());
    }

    @Test
    void createOrder() throws Exception {
        MenuItemRequestDTO itemDto1 = new MenuItemRequestDTO(42L, 2);
        MenuItemRequestDTO itemDto2 = new MenuItemRequestDTO(47L, 1);
        CreateOrderRequestDTO requestDto = new CreateOrderRequestDTO(
                9L,
                Arrays.asList(itemDto1, itemDto2),
                "Extra spicy, please"
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/app/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());


    }

    @Test
    void deleteOrder() throws Exception {

        mockMvc.perform(delete("/app/order/orders/{id}", 3))
                .andExpect(status().isNoContent());

    }

    @Test
    void getOrder() throws Exception {

        mockMvc.perform(get("/app/order/orders/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    void getAllOrders() throws Exception {

        mockMvc.perform(get("/app/order/my-orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }






}
