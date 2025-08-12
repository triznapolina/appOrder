package org.example.apporders.models.RequestsDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegistrationRequestDTO {


    @NotBlank
    private String username;

    @Pattern(regexp = "(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})")
    private String phone;

    @Min(4)
    private String password;

    public RegistrationRequestDTO(String username, String phone, String password) {
        this.username = username;
        this.phone = phone;
        this.password = password;

    }
}
