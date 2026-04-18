package com.example.rentcar.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentalRepo extends JpaRepository<RentalTable,Integer> {

    @Query("SELECT r FROM RentalTable r WHERE r.status = 'ACTIVE' and r.endDate <= CURRENT_TIMESTAMP")
    List<RentalTable> findCompleted();

    @Modifying
    @Transactional
    @Query("UPDATE  RentalTable r SET r.status = :status WHERE r.id=:id ")
    int updateStatus(@Param("status") RentalStatus status,@Param("id") Integer id);

    @Query("SELECT r FROM RentalTable r WHERE r.status=:status")
    List<RentalTable> findByRentalStatusEnum(@Param("status") RentalStatus status);

    @Query("SELECT r FROM RentalTable r WHERE r.status=:status or r.status=:status2 and r.user.userId = :userId")
    List<RentalTable> findBy2RentalStatusEnum(@Param("status") RentalStatus status,@Param("status2") RentalStatus status2,@Param("userId") UUID userId);



    @Query("SELECT r FROM RentalTable r WHERE r.status=:status and r.user.userId = :userId")
    List<RentalTable> findByRentalStatusEnumByUUID(@Param("status") RentalStatus status, @Param("userId") UUID userId);


    @Modifying
    @Transactional
    @Query("UPDATE RentalTable r SET r.penaltyAmount = r.penaltyAmount + :newPenalty WHERE r.id=:id")
    int newPenaltyAmount(@Param("newPenalty") BigDecimal newPenalty, @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE RentalTable r SET r.returnedAt= :returned WHERE r.id=:id")
    int returnedAt(@Param("returned") LocalDateTime returned, @Param("id") Integer id);

    @Query("SELECT r FROM RentalTable r WHERE r.id=:id")
    RentalTable findOne(@Param("id") Integer id);




}
