package com.example.rentcar.auth.customerauth;


import com.example.rentcar.user.UserRepo;
import com.example.rentcar.user.UserTable;
import com.example.rentcar.zcommon.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerAuthService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String Userlogin(LoginDto loginDto) {

        UserTable user = userRepo.getUserByEmail(loginDto.getEmail());
        if (user == null) {
            throw new RuntimeException("USER CANT FOUND");
        }
        boolean matchPassword = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
        if (!matchPassword) {
            throw new RuntimeException("PASSWORD INCORRECT");
        }

        return jwtService.generateToken(user.getUserId().toString());

    }

    @Transactional
    public void CreateAccount(CreateAccountDto createAccountDto) {
        UserTable user = userRepo.getUserByEmail(createAccountDto.getEmail());
        if (user != null) {
            throw new RuntimeException("EMAIL ALREADY EXISTS");
        }
        UUID createToken = UUID.fromString(jwtService.generateToken(UUID.randomUUID().toString()));

        UserTable userTable = UserTable.builder()
                .userId(createToken)
                .email(createAccountDto.getEmail())
                .password(passwordEncoder.encode(createAccountDto.getPassword()))
                .firstName(createAccountDto.getFirstName())
                .lastName(createAccountDto.getLastName())
                .phone(createAccountDto.getPhoneNumber())
                .role("USER")
                .build();
        userRepo.save(userTable);
    }
}
