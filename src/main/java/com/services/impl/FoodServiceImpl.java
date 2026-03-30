package com.services.impl;

import com.entity.FoodEntity;
import com.entity.RequestsDTO.UpdaterRequestFood;
import com.entity.dto.Food;
import com.entity.inHead.FilterRequest;
import com.mapper.FoodMapper;
import com.repositories.FoodRepository;
import com.services.FoodService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;


    @Override
    public Food createFood(Food food) {
        return foodMapper.toDto(foodRepository.save(foodMapper.toEntity(food)));
    }

    @Override
    public Food getById(Long id) {
        return foodMapper.toDto(foodRepository.findById(id).orElse(null));
    }

    @Override
    public Food deactivateStatus(Long id, boolean active) {
        foodRepository.setIsActive(id, false);
        return foodMapper.toDto(foodRepository.findById(id).orElse(null));
    }

    @Override
    public Food activateStatus(Long id, boolean active) {
        foodRepository.setIsActive(id, true);
        return foodMapper.toDto(foodRepository.findById(id).orElse(null));
    }


    @Override
    public Food deactivateFood(Long id, boolean active) {
        foodRepository.setIsDeleted(id, false);
        return foodMapper.toDto(foodRepository.findById(id).orElse(null));
    }


    @Override
    @Transactional
    public Food updateFood(Long foodId, UpdaterRequestFood food) {

        foodRepository.updateFood(foodId, food.getName(), food.getShortDescription(),
                                  food.getPrice(), food.getCategoryTypeId());

        foodRepository.setIsDeleted(foodId, false);
        foodRepository.setIsActive(foodId, true);

        FoodEntity foodEntity = foodRepository.findById(foodId).orElse(null);

        return foodMapper.toDto(foodEntity);
    }

    @Override
    public List<Food> deleteFood(Long foodId) {
        foodRepository.deleteById(foodId);

        return foodRepository.findAll().stream()
                .map(foodMapper::toDto)
                .toList();
    }


    @Override
    public List<Food> findByName(String name) {
        return foodRepository.findFoodByName(name).stream()
                .map(foodMapper::toDto)
                .toList();
    }

    @Override
    public List<Food> findByPriceBetween(BigDecimal minRange, BigDecimal maxRange) {
        return foodRepository.findFoodByPrice(minRange,maxRange).stream()
                .map(foodMapper::toDto)
                .toList();
    }

    @Override
    public List<Food> findByCategoryId(Long categoryId) {
        return foodRepository.findByCategoryId(categoryId).stream()
                .map(foodMapper::toDto)
                .toList();
    }

    @Override
    public Page<Food> getAllFoods(FilterRequest request) {
        return foodRepository.findAll(PageRequest.of(request.getPage(), request.getSize()))
                .map(foodMapper::toDto);
    }


}