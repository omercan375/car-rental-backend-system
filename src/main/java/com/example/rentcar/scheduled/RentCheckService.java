package com.example.rentcar.scheduled;

import com.example.rentcar.rental.RentalRepo;
import com.example.rentcar.rental.RentalStatus;
import com.example.rentcar.rental.RentalTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

@Service
public class RentCheckService {

    @Autowired
    private RentalRepo rentalRepo;


    @Transactional
    public void rentCheck() {
        // ZAMANI BİTMİŞ HEPSİNİ ALALIM
        List<RentalTable> rentalTables = rentalRepo.findCompleted();
        // ZAMANI BİTENLERİ APPROVE A ATALIM
        for (RentalTable rentalTable : rentalTables) {
            int c = rentalRepo.updateStatus(RentalStatus.WAITING_APPROVE,rentalTable.getId());
            if(c == 0 ){
                throw new RuntimeException("CANT UPDATE TO WAITINF_APPROVE");
            }
        }
    }
}
