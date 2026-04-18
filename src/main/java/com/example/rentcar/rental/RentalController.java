package com.example.rentcar.rental;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rental")
@CrossOrigin(origins = "*")

public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping("/rent")
    public ResponseEntity<?> rentCar(@RequestHeader("Authorization") String token, @Valid @RequestBody RentDto rentDto) {
        rentalService.rentCar(token, rentDto);
        return ResponseEntity.ok().body("rent successful");
    }

    @PostMapping("/cancel-rental")
    public ResponseEntity<?> cancelRental(@RequestHeader("Authorization") String token, @RequestParam int rentalId) {
        rentalService.cancelRental(token, rentalId);
        return ResponseEntity.ok().build();
    }
}
