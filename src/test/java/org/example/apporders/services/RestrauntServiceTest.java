package org.example.apporders.services;

import org.example.apporders.exception.ResourceNotFoundException;
import org.example.apporders.models.Restraunt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RestrauntServiceTest {

    @Autowired
    private RestrauntService restrauntService;


    @Test
    void getAllRestraunts() {
        List<Restraunt> allRstr = restrauntService.getAllRestraunts();
        System.out.println(allRstr);
        assertNotNull(allRstr, "not null");
    }

    @Test
    void getRestrauntByAddress() {

        Restraunt foundRestraunt = restrauntService.getRestrauntByAddress("Test Address");
        assertEquals("Test Address", foundRestraunt.getAddress());
    }

    @Test
    void getRestrauntByAddressNegative() {
        String nonExistingAddress = "address";

        Exception thrownException = assertThrows(
                ResourceNotFoundException.class,
                () -> restrauntService.getRestrauntByAddress(nonExistingAddress),
                "Should start RestrauntNotFoundException "
        );
    }

}

