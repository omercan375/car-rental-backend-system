package com.example.rentcar.auth.adminauth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AdminLoginDto {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
