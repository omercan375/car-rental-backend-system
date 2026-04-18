package com.example.rentcar.auth.customerauth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class LoginDto {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
