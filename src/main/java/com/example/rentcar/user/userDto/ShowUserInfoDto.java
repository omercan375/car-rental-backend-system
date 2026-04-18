package com.example.rentcar.user.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ShowUserInfoDto {
private String email;
private String firstName;
private String lastName;
private String phoneNumber;

}
