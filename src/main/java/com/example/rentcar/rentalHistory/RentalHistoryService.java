package com.example.rentcar.rentalHistory;


import com.example.rentcar.rental.RentalRepo;
import com.example.rentcar.rental.RentalStatus;
import com.example.rentcar.rental.RentalTable;
import com.example.rentcar.rentalHistory.rentalHistoryDto.HistoryDto;
import com.example.rentcar.user.UserRepo;
import com.example.rentcar.user.UserTable;
import com.example.rentcar.zcommon.JwtService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.UUID;

@Service
public class RentalHistoryService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RentalRepo rentalRepo;


    @Transactional
    public List<HistoryDto> getActiveRentalHistory(String token) {
        UUID extractToken = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("user not found");
        }
        List<RentalTable> rentals = rentalRepo.findByRentalStatusEnumByUUID(RentalStatus.ACTIVE, extractToken);
        return createHistoryList(rentals);
    }

    @Transactional
    public List<HistoryDto> getCompletedRentalHistory(String token) {
        UUID extractToken = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("user not found");
        }

        List<RentalTable> rentals = rentalRepo.findByRentalStatusEnumByUUID(RentalStatus.COMPLETED, extractToken);
        return createHistoryList(rentals);
    }

    @Transactional
    public List<HistoryDto> getPendingRentalHistory(String token) {
        UUID extractToken = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("user not found");
        }
        List<RentalTable> rentals = rentalRepo.findBy2RentalStatusEnum(RentalStatus.WAITING_APPROVE,RentalStatus.CANCEL_APPROVE,extractToken);
        return createHistoryList(rentals);

    }

    @Transactional
    public List<HistoryDto> getPenaltyHistory(String token) {
        UUID extractToken = jwtService.extractUserId(token);
        UserTable user = userRepo.getUserByUUID(extractToken);
        if (user == null) {
            throw new RuntimeException("user not found");
        }
        List<RentalTable> rentals = rentalRepo.findByRentalStatusEnumByUUID(RentalStatus.PENALTY, extractToken);
        return createHistoryList(rentals);

    }

    public List<HistoryDto> createHistoryList(List<RentalTable> rentalTable) {

        List<HistoryDto> historyDtoList = new ArrayList<>();
        for (RentalTable rentalTable1 : rentalTable) {
            String fullName = rentalTable1.getUser().getFirstName() + " " + rentalTable1.getUser().getLastName();
            HistoryDto historyDto = HistoryDto.builder()
                    .rentalId(rentalTable1.getId())
                    .brandName(rentalTable1.getCar().getModel().getBrand().getName())
                    .modelYear(rentalTable1.getCar().getModel().getYear())
                    .modelName(rentalTable1.getCar().getModel().getName())
                    .carPlate(rentalTable1.getCar().getPlate())
                    .userName(fullName)
                    .pickupLocation(rentalTable1.getPickupLocation().getName())
                    .dropoffLocation(rentalTable1.getDropoffLocation().getName())
                    .pickupDate(rentalTable1.getStartDate())
                    .dropoffDate(rentalTable1.getEndDate())
                    .totalDays(rentalTable1.getTotalDays())
                    .totalPrice(rentalTable1.getTotalPrice())
                    .notes(rentalTable1.getNotes())
                    .status(rentalTable1.getStatus().toString())
                    .createdAt(rentalTable1.getCreatedAt())
                    .build();
            historyDtoList.add(historyDto);
        }
        return historyDtoList;
    }

}
