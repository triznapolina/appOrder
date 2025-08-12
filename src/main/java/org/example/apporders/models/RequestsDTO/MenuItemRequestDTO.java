package org.example.apporders.models.RequestsDTO;

import lombok.Data;

@Data
public class MenuItemRequestDTO {
    private Long foodId;
    private int quantity;

    public MenuItemRequestDTO(long foodId, int quantity) {
        this.foodId = foodId;
        this.quantity = quantity;
    }
}
