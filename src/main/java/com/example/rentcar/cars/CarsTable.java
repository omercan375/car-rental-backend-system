package com.example.rentcar.cars;


import com.example.rentcar.model.ModelTable;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarsTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private ModelTable model;

    @Column(name = "plate")
    private String plate;
    @Column(name = "color")
    private String color;
    @Column(name = "mileage")
    private int mileage;
    @Column(name = "daily_price")
    private BigDecimal dailyPrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CarStatu status;
    @Column(name = "gear_type")
    private String gearType;
    @Column(name = "fuel_type")
    private String fuelType;
    @Column(name = "seat_count")
    private int seatCount;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

















}
