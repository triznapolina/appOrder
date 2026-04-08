package com.dto;

import lombok.Data;

@Data
public class Payment {

    private Long id;
    private String identificationNumber;
    private Long card_id;
    private String status;


}