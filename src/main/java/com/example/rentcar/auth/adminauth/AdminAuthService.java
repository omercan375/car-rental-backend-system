package com.example.rentcar.auth.adminauth;

import com.example.rentcar.admin.AdminRepo;
import com.example.rentcar.admin.AdminTable;
import com.example.rentcar.zcommon.JwtService;
import io.grpc.ClientStreamTracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;


    public String adminLogin(AdminLoginDto adminLoginDto) {
        AdminTable adminTable = adminRepo.findAdminByEmail(adminLoginDto.getEmail());
        if (adminTable == null) {
            throw new RuntimeException("NO ADMİN FOUND");
        }
//        boolean matchPassword = passwordEncoder.matches(adminLoginDto.getPassword(),adminTable.getPassword());
//        if (!matchPassword) {
//            throw new RuntimeException("PASSWORD INCORRECT");
//        }
        if (adminTable.getPassword().equals(adminLoginDto.getPassword())) {

            return jwtService.generateToken(adminTable.getAdminId().toString());
        }
        throw new RuntimeException("PASSWORD INCORRECT");
    }
}
