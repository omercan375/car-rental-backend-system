package com.example.rentcar.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletRepo extends JpaRepository<WalletTable, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE WalletTable w SET w.balance = w.balance - :amount WHERE w.balance >= :amount and w.user.userId=:userId")
    int updateBalance(@Param("amount") BigDecimal amount,@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query("UPDATE WalletTable w SET w.balance = w.balance + :balance WHERE w.user.userId=:userId")
    int returnBalance(@Param("balance") BigDecimal balance,@Param("userId") UUID userId);
}
