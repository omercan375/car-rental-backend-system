package com.example.rentcar.auth.customerauth;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/auth")
@CrossOrigin(origins = "*")

public class CustomerAuthController {
    @Autowired
    private CustomerAuthService customerAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid  @RequestBody LoginDto loginDto) {
        String token = customerAuthService.Userlogin(loginDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountDto createAccountDto) {
        customerAuthService.CreateAccount(createAccountDto);
        return ResponseEntity.ok("Account created");
    }
}
