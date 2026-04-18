package com.example.rentcar.model;

import com.example.rentcar.brand.BrandTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ModelRepo extends JpaRepository<ModelTable,Integer> {
}
