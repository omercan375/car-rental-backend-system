package com.example.rentcar.location;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {

private int id;
private String name;
private String city;
private String address;
private String phone;
private boolean isActive;

}
