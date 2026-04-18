package com.example.rentcar.adminApprove;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproveDto {
    private int RentalId;
    private int carId;
    private String brandName;
    private int modelYear;
    private String modelName;
    private String carPlate;
    private String userName;
    private String pickupLocation;
    private String dropoffLocation;
    private LocalDateTime pickupDate;
    private LocalDateTime dropoffDate;
    private int totalDays;
    private BigDecimal totalPrice;
    private String notes;
    private LocalDateTime createdAt;

}
