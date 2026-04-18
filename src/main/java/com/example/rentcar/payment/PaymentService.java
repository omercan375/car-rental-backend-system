package com.example.rentcar.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private WalletRepo walletRepo;

    @Transactional
    public boolean pay(BigDecimal amount, UUID userId) {

        int updateBalance = walletRepo.updateBalance(amount, userId);
        if (updateBalance == 0) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean returnPay(BigDecimal amount, UUID userId) {
        int returnBalance = walletRepo.returnBalance(amount, userId);
        if (returnBalance == 0) {
            throw new RuntimeException("CANT RETURNED");


        }
        return true;
    }
}
