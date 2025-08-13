package org.example.apporders.services.impl;

import org.example.apporders.exception.ResourceNotFoundException;
import org.example.apporders.models.Restaurant;
import org.example.apporders.repositories.RestrauntsRepository;
import org.example.apporders.services.RestrauntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RestaurantServiceImpl implements RestrauntService {

    @Autowired
    private RestrauntsRepository restrauntsRepository;


    @Override
    public List<Restaurant> getAllRestraunts(){
        return restrauntsRepository.findAll();
    }

    @Override
    public Restaurant getRestrauntByAddress(String address){

        if(restrauntsRepository.findByAddress(address)==null){
            throw new ResourceNotFoundException("Restaurant not found with address: " + address);
        }

        return restrauntsRepository.findByAddress(address);

    }

}
