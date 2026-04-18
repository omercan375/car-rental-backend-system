package com.example.rentcar.scheduled.penaltyCalculation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PenaltyController {
    @Autowired
    private PenaltyService penaltyService;

    @Scheduled(cron = "0 0 23 * * ?")
    public void calculatePenalty() {
        penaltyService.penaltyCalculation();
    }

}
