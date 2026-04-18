package com.example.rentcar.user;


import com.example.rentcar.user.userDto.ShowUserInfoDto;
import com.example.rentcar.zcommon.JwtService;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.firebase.auth.UserInfo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ShowUserInfoDto getUserInfo(String token) {
        UUID extractToken = jwtService.extractUserId(token);
        // MÜŞTERİ KONTROLÜ
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("CUSTOMER DONT FOUND");
        }
       ShowUserInfoDto showUserInfoDto = ShowUserInfoDto.builder()
               .email(user.getEmail())
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .phoneNumber(user.getPhone())
               .build();
        return showUserInfoDto;

    }
    public void changeEmail(String token, String newEmail) {
        UUID extractToken = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("CUSTOMER DONT FOUND");
        }
        // EMAİL BAŞKA Bİ KİŞİDE VARMI KONTROLÜ
        UserTable newUser = userRepo.getUserByEmail(newEmail);
        if (newUser != null) {
            throw new RuntimeException("EMAİL ALREADY EXISTS");
        }
        // YENİ EMAİLİ KAYIT ET
        int changeEmail = userRepo.changeEmail(newEmail, extractToken);
        if (changeEmail == 0) {
            throw new RuntimeException("EMAİL CANT UPDATE");
        }

    }
    public void changePassowrd(String token, String newPassword,String oldPassword) {
        UUID extractToken = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("CUSTOMER DONT FOUND");
        }
        //DB DEKİ ŞİFRE İLE GİRİLEN ŞİFRE EŞLEŞİYORMU
        boolean matchPassword = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!matchPassword) {
            throw new RuntimeException("PASSWORD DOES NOT MATCH");
        }
        // YENİ ŞİFREYİ HASHLEYİP KAYIT ET
        String hashedNewPassword = passwordEncoder.encode(newPassword);
        int changePassword = userRepo.changePassword(hashedNewPassword, extractToken);
        if (changePassword == 0) {
            throw new RuntimeException("PASSWORD DOES NOT CHANGE");
        }
    }

    public void changePhone(String token, String newPhone) {
        UUID extractToken = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("CUSTOMER DONT FOUND");
        }
        // TELEFON NUMARASININ BAŞKA BİRİNDE OLUP OLMADIĞI KONTROLÜ
        UserTable newUser = userRepo.getUserByPhone(newPhone);
        if (newUser != null) {
            throw new RuntimeException("PHONE EXIST");
        }
        int changePhone = userRepo.changePhone(newPhone, extractToken);
        if (changePhone == 0) {
            throw new RuntimeException("PHONE DOES NOT CHANGE");
        }

    }
}
