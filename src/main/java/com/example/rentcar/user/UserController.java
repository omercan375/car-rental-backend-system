package com.example.rentcar.user;


import com.example.rentcar.user.userDto.ShowUserInfoDto;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        ShowUserInfoDto showUserInfoDto =  userService.getUserInfo(token);
        return ResponseEntity.ok(showUserInfoDto);
    }
    @PatchMapping("/change/email")
    public ResponseEntity<?> changeEmail(@RequestHeader("Authorization") String token, @RequestParam("email") String email) {
        userService.changeEmail(token, email);
        return ResponseEntity.ok().body("email has been changed successfully");
    }

    @PatchMapping("/change/password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.changePassowrd(token,newPassword,oldPassword);
        return ResponseEntity.ok().body("password has been changed successfully");
    }

    @PatchMapping("/change/phone")
    public ResponseEntity<?> changePhone(@RequestHeader("Authorization") String token, @RequestParam String newPhone) {
        userService.changePhone(token,newPhone);
        return ResponseEntity.ok().body("phone has been changed successfully");
    }
}
