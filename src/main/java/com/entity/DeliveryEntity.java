package com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "delivery")
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "payment_type", nullable = false)
    private Boolean paymentType = false;

    @NotNull
    @Column(name = "time_delivery", nullable = false, length = Integer.MAX_VALUE)
    private String timeDelivery;

    @NotNull
    @Column(name = "is_inPlace", nullable = false)
    private Boolean isInplace = false;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

}