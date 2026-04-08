package com.services;

import com.dto.Restaurant;
import com.inHead.FilterRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RestaurantService {

    Restaurant findById(long id);

    List<Restaurant> deleteRestaurantById(long id);

    Page<Restaurant> getAllRestaurants(FilterRequest filterRequest);

    Restaurant getRestaurantByAddress(String address);

    Restaurant createRestaurant(Restaurant restaurant);

    Restaurant updateRestaurant(Long id, Restaurant restaurant);

}
