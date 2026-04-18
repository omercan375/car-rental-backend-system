package com.example.rentcar.location;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = "*")

public class LocationController {
    @Autowired
    private LocationService locationService;


    @GetMapping("/show")
    public ResponseEntity<?> getAllLocations(@RequestHeader("Authorization") String token) {
        List<LocationDto> locations = locationService.getAllLocations(token);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }
}
