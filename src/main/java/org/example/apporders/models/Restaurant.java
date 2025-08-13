package org.example.apporders.models;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restraunts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Ресторан")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String address;

    public Restaurant(String phone, String address) {
        this.phone = phone;
        this.address = address;

    }
}
