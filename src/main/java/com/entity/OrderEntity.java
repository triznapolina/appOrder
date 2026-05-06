package com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.inHead.JpaEnable;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "customer_orders")
public class OrderEntity extends JpaEnable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurantEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentEntity;

    @Column(name = "status",  length = Integer.MAX_VALUE)
    private String status;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

    @Column(name = "short_description", length = Integer.MAX_VALUE)
    private String shortDescription;

    @Column(name = "total_price",  precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "is_completed")
    private Boolean isCompleted;
}