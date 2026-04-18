package com.example.rentcar.payment;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")

public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody BigDecimal amount, @RequestParam UUID userId) {
        paymentService.pay(amount, userId);
        return ResponseEntity.ok().build();
    }
}
