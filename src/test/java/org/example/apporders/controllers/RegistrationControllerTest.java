package org.example.apporders.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apporders.models.RequestsDTO.RegistrationRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception {
        RegistrationRequestDTO request = new RegistrationRequestDTO(
                "some",
                "+375338595474",
                "1254"
        );

        mockMvc.perform(post("/app/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

    }



    @Test
    void registerAuthorizedUserByNameAndPhone() throws Exception {
        RegistrationRequestDTO request = new RegistrationRequestDTO(
                "some",
                "+375338595474",
                "1254"
        );

        mockMvc.perform(post("/app/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void registerUserWithInvalidPhoneFormat() throws Exception {
        RegistrationRequestDTO request = new RegistrationRequestDTO(
                "new",
                "1458275p",
                "1254"
        );

        mockMvc.perform(post("/app/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }




}
