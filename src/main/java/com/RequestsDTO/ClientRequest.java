package com.RequestsDTO;

import lombok.Data;

@Data
public class ClientRequest {

    private Long id;
    private String email;
    private String phoneNumber;
    private String fullName;
}
