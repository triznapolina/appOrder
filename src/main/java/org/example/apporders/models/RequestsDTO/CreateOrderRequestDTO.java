package org.example.apporders.models.RequestsDTO;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestDTO {

    private Long restaurantId;

    private List<MenuItemRequestDTO> menuItems;

    private String comment;

    public CreateOrderRequestDTO(long restaurantId, List<MenuItemRequestDTO> menuItems, String comment) {
        this.restaurantId = restaurantId;
        this.menuItems = menuItems;
        this.comment = comment;
    }

    public List<MenuItemRequestDTO> getMenuItems() {
        return menuItems;
    }
}
