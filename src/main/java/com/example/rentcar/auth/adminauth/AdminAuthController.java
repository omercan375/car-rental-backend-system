package com.example.rentcar.auth.adminauth;


import com.example.rentcar.admin.AdminTable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
@CrossOrigin(origins = "*")
public class AdminAuthController {
    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@Valid @RequestBody AdminLoginDto adminLoginDto) {
        String token = adminAuthService.adminLogin(adminLoginDto);
        return ResponseEntity.ok(token);
    }
}
