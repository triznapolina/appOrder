package com.RequestsDTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdaterRequestFood {
    private String name;
    private String shortDescription;
    private BigDecimal price;
    private Long categoryTypeId;

}
