package com.example.rentcar.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface UserRepo extends JpaRepository<UserTable, UUID> {

    @Query("SELECT u FROM UserTable u WHERE u.userId=:userId")
    UserTable getUserByUUID(@RequestParam("userId")  UUID userId);

    @Query("SELECT u FROM UserTable u WHERE u.email = :email")
    UserTable getUserByEmail(@RequestParam("email")  String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserTable u SET u.email = :newEmail WHERE u.userId = :userId")
    int changeEmail(@RequestParam("newEmail") String newEmail, @RequestParam("userId")  UUID userId );

    @Modifying
    @Transactional
    @Query("UPDATE UserTable u SET u.password = :password WHERE u.userId=:userId")
    int changePassword(@RequestParam("password") String password, @RequestParam("userId")  UUID userId);

    @Query("SELECT u FROM UserTable u WHERE u.phone=:phoneNumber")
    UserTable getUserByPhone(@RequestParam("phoneNumber") String phoneNumber);

    @Modifying
    @Transactional
    @Query("UPDATE UserTable u SET u.phone=:phoneNumber WHERE u.userId=:userId")
    int changePhone(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("userId")  UUID userId);

}
