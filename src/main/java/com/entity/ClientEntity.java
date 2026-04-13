package com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.inHead.JpaEnable;

@Getter
@Setter
@Entity
@Table(name = "client")
public class ClientEntity extends JpaEnable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @Column(name = "verification_code", length = Integer.MAX_VALUE)
    private String verificationCode;

    @Column(name = "phone_number", length = Integer.MAX_VALUE)
    private String phoneNumber;

    @Column(name = "full_name", length = Integer.MAX_VALUE)
    private String fullName;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity cardEntity;

}