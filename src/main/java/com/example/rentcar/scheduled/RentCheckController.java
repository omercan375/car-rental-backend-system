package com.example.rentcar.scheduled;

import com.example.rentcar.rental.RentalRepo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RentCheckController {
    @Autowired
    private  RentCheckService rentCheckService;

    @Scheduled(cron = "0 0 23 * * ?")
    public void checkRental(){
        rentCheckService.rentCheck();
    }
}
