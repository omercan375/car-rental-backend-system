package com.example.rentcar.model;

import com.example.rentcar.brand.BrandTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "models")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ModelTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandTable brand;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
