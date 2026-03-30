package com.services.impl;

import com.entity.dto.Restaurant;
import com.entity.inHead.FilterRequest;
import com.mapper.RestaurantMapper;
import com.repositories.RestaurantRepository;
import com.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;


    @Override
    public Restaurant findById(long id) {
        return restaurantMapper.toDto(restaurantRepository.findById(id).orElse(null));
    }

    @Override
    public List<Restaurant> deleteRestaurantById(long id) {
        restaurantRepository.deleteById(id);

        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDto)
                .toList();
    }

    @Override
    public Page<Restaurant> getAllRestaurants(FilterRequest request) {
        return restaurantRepository.findAll(PageRequest.of(request.getPage(), request.getSize()))
                .map(restaurantMapper::toDto);
    }

    @Override
    public Restaurant getRestaurantByAddress(String address) {
        return restaurantMapper.toDto(restaurantRepository.findByAddress(address));
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantMapper.toDto(restaurantRepository.save(restaurantMapper.toEntity(restaurant)));
    }

    @Override
    @Transactional
    public Restaurant updateRestaurant(Long id, Restaurant restaurant) {

        restaurantRepository.updateRestaurantInfo(id, restaurant.getAddress(), restaurant.getPhone());

        return restaurantMapper.toDto(restaurantRepository.findById(id).orElse(null));
    }
}