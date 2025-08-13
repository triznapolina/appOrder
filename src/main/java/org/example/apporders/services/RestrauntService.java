package org.example.apporders.services;

import org.example.apporders.models.Restaurant;

import java.util.List;

public interface RestrauntService {

    List<Restaurant> getAllRestraunts();

    Restaurant getRestrauntByAddress(String address);



}
