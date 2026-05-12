package com.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Food {

    private Long id;
    private String name;
    private String ingredientsDescription;
    private String shortDescription;
    private BigDecimal price;
    private Long categoryId;
    private Boolean isActive;
    private LocalDate createdAt;
    private String imageUrl;

}