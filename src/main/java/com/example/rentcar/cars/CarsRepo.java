package com.example.rentcar.cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarsRepo extends JpaRepository<CarsTable,Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE CarsTable c SET c.status =:status WHERE c.id = :id and c.status='AVAILABLE'")
    int updateCarStatus(@Param("id") Integer id, @Param("status") CarStatu status);

    @Query("SELECT c FROM CarsTable c WHERE c.id=:id")
    CarsTable findRezerved(@Param("id") Integer id);

    @Query("SELECT c FROM CarsTable c WHERE c.status=:status")
    List<CarsTable> findCarsByStatus(@Param("status") CarStatu status);

    @Modifying
    @Transactional
    @Query("UPDATE CarsTable c SET c.status =:status WHERE c.id=:id")
    int makeCarFree(@Param("status") CarStatu status,@Param("id") Integer id);
}
