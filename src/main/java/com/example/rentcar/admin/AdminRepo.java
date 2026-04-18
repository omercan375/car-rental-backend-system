package com.example.rentcar.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AdminRepo extends JpaRepository<AdminTable, Integer> {
    @Query("SELECT a FROM AdminTable a WHERE a.email=:email")
    AdminTable findAdminByEmail(@Param("email") String email);

    @Query("SELECT a FROM AdminTable a WHERE a.adminId=:adminId")
    AdminTable findAdminById(@Param("adminId") UUID adminId);
}
