package com.entity.dto;


import lombok.Data;

@Data
public class Client  {

    private Long id;
    private String email;
    private String phoneNumber;
    private String fullName;

}