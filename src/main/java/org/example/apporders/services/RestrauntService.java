package org.example.apporders.services;

import org.example.apporders.models.Restraunt;

import java.util.List;

public interface RestrauntService {

    List<Restraunt> getAllRestraunts();

    Restraunt getRestrauntByAddress(String address);



}
