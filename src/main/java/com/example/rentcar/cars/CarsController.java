package com.example.rentcar.cars;


import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ListResourceBundle;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*")
public class CarsController {
    @Autowired
    private CarsService carsService;

    @GetMapping("/show")
    public ResponseEntity<List<CarsDto>> showCars(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(carsService.showCars(token));
    }

}
