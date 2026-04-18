package com.example.rentcar.cars;


import com.example.rentcar.model.ModelDto;
import io.grpc.ClientStreamTracer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarsDto {
private int carId;
private ModelDto  model;
private String plate;
private String color;
private int mileage;
private BigDecimal dailyPrice;
private String status;
private String gearType;
private String fuelType;
private int seatCount;





}
