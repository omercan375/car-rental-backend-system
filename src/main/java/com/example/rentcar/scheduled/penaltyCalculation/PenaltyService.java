package com.example.rentcar.scheduled.penaltyCalculation;

import com.example.rentcar.rental.RentalRepo;
import com.example.rentcar.rental.RentalStatus;
import com.example.rentcar.rental.RentalTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Service
public class PenaltyService {
    @Autowired
    private RentalRepo rentalRepo;


    @Transactional
    public void penaltyCalculation() {
        List<RentalTable> penaltyRentals = rentalRepo.findByRentalStatusEnum(RentalStatus.PENALTY);

        for(RentalTable rentalTable : penaltyRentals) {
            // CALCULATE PENALTY
            BigDecimal dailyPrice = rentalTable.getDailyPrice();
            BigDecimal halfPrice = rentalTable.getDailyPrice().divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
            BigDecimal total = dailyPrice.add(halfPrice);
            int newPenalty = rentalRepo.newPenaltyAmount(total,rentalTable.getId());
            if(newPenalty == 0) {
                throw new RuntimeException("CANT ADD NEW PENALTY");
            }
        }
    }

}
