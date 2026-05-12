package com.dto;


import com.entity.ClientEntity;
import lombok.Data;

@Data
public class Client  {

    private Long id;
    private String email;
    private ClientEntity.Role role;
    private String address;
    private String phoneNumber;
    private String fullName;
    private Boolean status;

}