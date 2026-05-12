package com.dto;


import lombok.Data;

@Data
public class Client  {

    private Long id;
    private String email;
    private String address;
    private String phoneNumber;
    private String fullName;
    private Boolean status;

}