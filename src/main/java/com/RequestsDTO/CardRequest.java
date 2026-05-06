package com.RequestsDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardRequest {
    private String cardNumber;
    private String holderName;
    private LocalDate expiry;
    private int cvcNumber;
}
