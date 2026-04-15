package com.dto;

import lombok.Data;

@Data
public class UserInfo {

    private Long id;
    private String email;
    private String role;

    private String phoneNumber;
    private String fullName;

}
