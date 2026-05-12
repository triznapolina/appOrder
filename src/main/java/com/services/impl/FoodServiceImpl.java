package com.services.impl;

import com.entity.FoodEntity;
import com.RequestsDTO.UpdaterRequestFood;
import com.dto.Food;
import com.inHead.FilterRequest;
import com.mapper.FoodMapper;
import com.repository.FoodRepository;
import com.services.FoodService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;


    @Override
    public Food createFood(Food food, MultipartFile image) {

        String fileName = null;

        String original = image.getOriginalFilename();

        String extension = original != null && original.contains(".")
                ? original.substring(original.lastIndexOf("."))
                : ".jpg";


        if (image != null && !image.isEmpty()) {
            try {
                fileName = UUID.randomUUID() + extension;

                Path path = Paths.get(System.getProperty("user.dir"), "uploads", fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, image.getBytes());

            } catch (IOException e) {
                throw new RuntimeException("Ошибка сохранения файла", e);
            }
        }

        FoodEntity entity = foodMapper.toEntity(food);
        entity.setImagePath(fileName);

        return foodMapper.toDto(foodRepository.save(entity));


    }

    @Override
    public Food getById(Long id) {

        FoodEntity entity = foodRepository.findById(id).orElse(null);

        if (entity == null) return null;

        Food dto = foodMapper.toDto(entity);

        if (entity.getImagePath() != null) {
            dto.setImageUrl("http://localhost:8080/catalog/images/" + entity.getImagePath());
        }

        return dto;
    }


    @Override
    @Transactional
    public Food updateFood(Long foodId, UpdaterRequestFood food, MultipartFile image) {

        FoodEntity foodEntity = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Блюдо не найдено"));

        foodRepository.updateFood(foodId, food.getName(), food.getShortDescription(),
                food.getPrice(), food.getCategoryId());

        if (image != null && !image.isEmpty()) {

            try {

                String original = image.getOriginalFilename();

                String extension = original != null && original.contains(".")
                        ? original.substring(original.lastIndexOf("."))
                        : ".jpg";

                String fileName = UUID.randomUUID() + extension;

                Path path = Paths.get(System.getProperty("user.dir"),
                        "uploads",
                        fileName);

                Files.createDirectories(path.getParent());

                Files.write(path, image.getBytes());

                foodEntity.setImagePath(fileName);

            } catch (IOException e) {
                throw new RuntimeException("Ошибка сохранения файла", e);
            }
        }

        return foodMapper.toDto(foodRepository.save(foodEntity));
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
        return foodRepository.findByPriceBetween(minRange,maxRange).stream()
                .map(foodMapper::toDto)
                .toList();
    }

    @Override
    public List<Food> filterByPriceBetweenAndCategory(Long categoryId, BigDecimal minRange, BigDecimal maxRange) {
        return foodRepository.findFoodByPriceAndCategory(categoryId, minRange, maxRange).stream()
                .map(foodMapper::toDto)
                .toList();
    }

    @Override
    public List<Food> findByCategoryId(Long categoryId) {
        return foodRepository.findByCategoryEntityId(categoryId).stream()
                .map(foodMapper::toDto)
                .toList();
    }

    @Override
    public Page<Food> getAllFoods(FilterRequest request) {
        return foodRepository.findAll(PageRequest.of(request.getPage(), request.getSize()))
                .map(foodMapper::toDto);
    }


}