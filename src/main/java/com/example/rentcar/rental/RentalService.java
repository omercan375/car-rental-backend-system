package com.example.rentcar.rental;

import com.example.rentcar.cars.CarStatu;
import com.example.rentcar.cars.CarsRepo;
import com.example.rentcar.cars.CarsTable;
import com.example.rentcar.location.LocationRepo;
import com.example.rentcar.location.LocationTable;
import com.example.rentcar.payment.PaymentService;
import com.example.rentcar.user.UserRepo;
import com.example.rentcar.user.UserTable;
import com.example.rentcar.zcommon.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RentalService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CarsRepo carsRepo;
    @Autowired
    private LocationRepo locationRepo;
    @Autowired
    private RentalRepo rentalRepo;
    @Autowired
    private PaymentService paymentService;


    @Transactional
    public void rentCar(String token, RentDto rentDto) {
        UUID extractToken = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("user not found");
        }

        // ARABAYI REZERVE EDELİM
        int updateCarStatus = carsRepo.updateCarStatus(rentDto.getCarId(), CarStatu.REZERVED);
        if (updateCarStatus == 0) {
            throw new RuntimeException("ALREADY REZERVED");
        }

        //ARABANIN BİLGİLERİNİ GETİR
        CarsTable carsTable = carsRepo.findRezerved(rentDto.getCarId());

        //KİRALAMA TARİHLERİNİ KONTROL EDELİM
        long rentDays = ChronoUnit.DAYS.between(rentDto.getStartDate(), rentDto.getEndDate());
        if (rentDays <= 0) {
            throw new RuntimeException("DATE PROBLEM");
        }

        // TOPLAM FİYATI HESAPLAYALIM
        BigDecimal totalRentAmount = carsTable.getDailyPrice().multiply(new BigDecimal(rentDays));
        // KİRALAMA MEKANLARINI KONTROL EDELİM
        LocationTable PickUpLocation = locationRepo.getLocationById(rentDto.getPickupId());
        LocationTable DropLocation = locationRepo.getLocationById(rentDto.getDropoffId());
        if (PickUpLocation == null || DropLocation == null) {
            throw new RuntimeException("LOCATION PROBLEM");
        }
        //KİRALAMA KAYDI OLUŞTURALIM
        RentalTable newRent = RentalTable.builder()
                .car(carsTable)
                .user(user)
                .pickupLocation(PickUpLocation)
                .dropoffLocation(DropLocation)
                .startDate(rentDto.getStartDate())
                .endDate(rentDto.getEndDate())
                .totalDays((int) rentDays)
                .dailyPrice(carsTable.getDailyPrice())
                .totalPrice(totalRentAmount)
                .notes(rentDto.getNotes())
                .status(RentalStatus.ACTIVE)
                .build();

        boolean payResult = paymentService.pay(totalRentAmount, extractToken);
        if (!payResult) {
            throw new RuntimeException("PAY FAILED");

        }
        rentalRepo.save(newRent);


    }



    public void cancelRental(String token, int rentalId){
        UUID extractToken = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("user not found");
        }

        // KİRALAMAYI BULALIM
        RentalTable rental = rentalRepo.findOne(rentalId);
        if (rental == null) {
            throw new RuntimeException("RENTAL NOT FOUND");
        }
        // ADMİNE ONAYLAMASI İÇİN GÖNDERELİM
        int updateStatus = rentalRepo.updateStatus(RentalStatus.CANCEL_APPROVE,rentalId);
        if (updateStatus == 0) {
            throw new RuntimeException("UPDATE PROBLEM");
        }

    }

}
