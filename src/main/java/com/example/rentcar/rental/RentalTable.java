package com.example.rentcar.rental;

import com.example.rentcar.cars.CarStatu;
import jakarta.persistence.Entity;


import com.example.rentcar.cars.CarsTable;
import com.example.rentcar.location.LocationTable;
import com.example.rentcar.user.UserTable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private CarsTable car;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserTable user;


    @ManyToOne
    @JoinColumn(name = "pickup_location_id", nullable = false)
    private LocationTable pickupLocation;

    @ManyToOne
    @JoinColumn(name = "dropoff_location_id", nullable = false)
    private LocationTable dropoffLocation;


    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;


    @Column(name = "total_days")
    private Integer totalDays;

    @Column(name = "daily_price", precision = 10, scale = 2)
    private BigDecimal dailyPrice;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;


    @Column(name = "notes")
    private String notes;


    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RentalStatus status;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Column(name = "penalty_amount", precision = 10, scale = 2)
    private BigDecimal penaltyAmount;
}