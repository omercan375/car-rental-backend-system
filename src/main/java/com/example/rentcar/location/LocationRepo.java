package com.example.rentcar.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepo extends JpaRepository<LocationTable, Integer> {
    @Query("SELECT l FROM LocationTable l")
    List<LocationTable> getAllLocations();

    @Query("SELECT l FROM LocationTable l WHERE l.id=:id")
    LocationTable getLocationById(@Param("id") Integer id);

}
