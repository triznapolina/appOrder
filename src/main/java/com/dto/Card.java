package com.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Card {

    private Long id;

    private String holder;

    private String number;

    private LocalDate expirationDate;

    private Integer cvcNumber;

}