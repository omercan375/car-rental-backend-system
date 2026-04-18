package com.example.rentcar.location;


import com.example.rentcar.user.UserRepo;
import com.example.rentcar.user.UserTable;
import com.example.rentcar.zcommon.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocationService {
    @Autowired
    private JwtService  jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LocationRepo locationRepo;


    public List<LocationDto>  getAllLocations(String token) {
        UUID extractId = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractId);
        if(user==null){
            throw new RuntimeException("User not found");
        }
        List<LocationDto> locationDtos = new ArrayList<>();
        List<LocationTable> locations = locationRepo.getAllLocations();
        if(locations.isEmpty()){
            throw new RuntimeException("No locations found");
        }

        for(LocationTable location : locations){
            LocationDto locationDto =  LocationDto.builder()
                    .id(location.getId())
                    .name(location.getName())
                    .phone(location.getPhone())
                    .address(location.getAddress())
                    .city(location.getCity())
                    .build();
            locationDtos.add(locationDto);
        }
        return locationDtos;
    }
}
