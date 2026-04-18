package com.example.rentcar.rentalHistory;

import com.example.rentcar.rentalHistory.rentalHistoryDto.HistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rental/history")
@CrossOrigin(origins = "*")

public class RentalHistoryController {
    @Autowired
    private RentalHistoryService rentalHistoryService;

    @GetMapping("/active")
    public ResponseEntity<?> getActiveRentalHistory(@RequestHeader("Authorization") String token){
        List<HistoryDto> rentals = rentalHistoryService.getActiveRentalHistory(token);
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/completed")
    public ResponseEntity<?> getCompletedRentalHistory(@RequestHeader("Authorization") String token){
        List<HistoryDto> rentals = rentalHistoryService.getCompletedRentalHistory(token);
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/waiting-approval")
    public ResponseEntity<?> getWaitingApprovalRentalHistory(@RequestHeader("Authorization") String token){
        List<HistoryDto> rentals = rentalHistoryService.getPendingRentalHistory(token);
        return ResponseEntity.ok(rentals);
    }
    @GetMapping("/penalty")
    public ResponseEntity<?> getPenaltyRentalHistory(@RequestHeader("Authorization") String token){
        List<HistoryDto> rentals =rentalHistoryService.getPenaltyHistory(token);
        return ResponseEntity.ok(rentals);
    }
}
