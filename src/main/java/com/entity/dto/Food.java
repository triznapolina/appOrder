package com.entity.dto;

import com.entity.inHead.JpaEnable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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