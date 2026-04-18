package com.example.rentcar.model;


import com.example.rentcar.brand.BrandDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelDto {
private BrandDto brand;
private String name;
private int year;

}

