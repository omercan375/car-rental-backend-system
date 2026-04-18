package com.example.rentcar.adminApprove;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approve")
@CrossOrigin(origins = "*")
public class ApproveController {

    @Autowired
    private ApproveService approveService;

    @GetMapping("/show-all")
    public ResponseEntity<?> showAll(@RequestHeader("Authorization") String token) {
        List<ApproveDto> showAllAproves = approveService.approve(token);
        return ResponseEntity.ok().body(showAllAproves);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> confirm(@RequestHeader("Authorization") String token, @RequestParam int rentalId, @RequestParam int carId) {
        approveService.completeApprove(token, rentalId, carId);
        return ResponseEntity.ok().body("Approve Complete");
    }

    @PostMapping("/penalty")
    public ResponseEntity<?> penalty(@RequestHeader("Authorization") String token, @RequestParam int rentalId) {
        approveService.penaltyApprove(token, rentalId);
        return ResponseEntity.ok().body("Penalty Approve");
    }


}
