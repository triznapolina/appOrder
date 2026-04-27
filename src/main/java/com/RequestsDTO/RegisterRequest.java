package com.RequestsDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    private String fullName;

    @Size(min = 5, max = 30, message = "Email should contains from 10 to 30 symbols")
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email should have a pattern like 'user@some.com'")
    private String email;

    @Size(min = 8, max = 16, message = "Password should contains from 8 to 20 symbols")
    @NotBlank(message = "Password can't be empty")
    private String password;
}
