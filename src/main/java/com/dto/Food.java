package com.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Food {

    private Long id;
    private String name;
    private String shortDescription;
    private BigDecimal price;
    private String categoryType;
    private Boolean isActive;
    private Boolean isDeleted;

}