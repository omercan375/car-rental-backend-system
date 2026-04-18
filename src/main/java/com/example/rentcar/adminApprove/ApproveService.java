package com.example.rentcar.adminApprove;


import com.example.rentcar.admin.AdminRepo;
import com.example.rentcar.admin.AdminTable;
import com.example.rentcar.cars.CarStatu;
import com.example.rentcar.cars.CarsRepo;
import com.example.rentcar.rental.RentalRepo;
import com.example.rentcar.rental.RentalService;
import com.example.rentcar.rental.RentalStatus;
import com.example.rentcar.rental.RentalTable;
import com.example.rentcar.rentalHistory.rentalHistoryDto.HistoryDto;
import com.example.rentcar.zcommon.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.UUID;

@Slf4j
@Service
public class ApproveService {
    @Autowired
    private RentalRepo rentalRepo;
    @Autowired
    private CarsRepo carsRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AdminRepo adminRepo;

    public List<ApproveDto>  getApproveList(List<RentalTable> rentalTables){
        List<ApproveDto> approveDtos = new ArrayList<>();
        for (RentalTable rentalTable : rentalTables) {
            String fullname = rentalTable.getUser().getFirstName() + " " + rentalTable.getUser().getLastName();
            ApproveDto approveDto = ApproveDto.builder()
                    .RentalId(rentalTable.getId())
                    .carId(rentalTable.getCar().getId())
                    .brandName(rentalTable.getCar().getModel().getBrand().getName())
                    .modelYear(rentalTable.getCar().getModel().getYear())
                    .modelName(rentalTable.getCar().getModel().getName())
                    .carPlate(rentalTable.getCar().getPlate())
                    .userName(fullname)
                    .pickupLocation(rentalTable.getPickupLocation().getName())
                    .dropoffLocation(rentalTable.getDropoffLocation().getName())
                    .pickupDate(rentalTable.getStartDate())
                    .dropoffDate(rentalTable.getEndDate())
                    .totalDays(rentalTable.getTotalDays())
                    .totalPrice(rentalTable.getTotalPrice())
                    .notes(rentalTable.getNotes())
                    .createdAt(rentalTable.getCreatedAt())
                    .build();

            approveDtos.add(approveDto);
        }
        return approveDtos;
    }


    @Transactional
    public List<ApproveDto> approve(String token) {
        UUID extractToken = jwtService.extractUserId(token);
        AdminTable admin = adminRepo.findAdminById(extractToken);
        if (admin == null) {
            throw new RuntimeException("NO ADMİN FOUNDED");
        }

        List<RentalTable> getAll = rentalRepo.findByRentalStatusEnum(RentalStatus.WAITING_APPROVE);
        if(getAll.isEmpty()){
            throw new RuntimeException("NO FOUNDED");
        }


        return getApproveList(getAll);}

    @Transactional
    public void completeApprove(String token ,int rentalId, int carId) {
        UUID extractToken = jwtService.extractUserId(token);
        AdminTable admin = adminRepo.findAdminById(extractToken);
        if (admin == null) {
            throw new RuntimeException("NO ADMİN FOUNDED");
        }
        // KİRALAMAYI COMPLETE YAPALIM
        int completeRental = rentalRepo.updateStatus(RentalStatus.COMPLETED,rentalId);
        if (completeRental == 0) {
            throw new RuntimeException("Can't complete approve");
        }
        // KİRALAMADAKİ RETURNED DATE I GÜNCELLEYELİM
        int updateReturned = rentalRepo.returnedAt(LocalDateTime.now(),rentalId);
        if (updateReturned == 0) {
            throw new RuntimeException("Can't complete approve");
        }
        // ARABAYI BOŞA ÇIKARTALIM
        int makeCarFree = carsRepo.makeCarFree(CarStatu.AVAILABLE,carId);
        if (makeCarFree == 0) {
            throw new RuntimeException("Can't UPDATE CAR TO AVAILABLE");
        }
    }


    @Transactional
    public void penaltyApprove(String token ,int rentalId){
        UUID extractToken = jwtService.extractUserId(token);
        AdminTable admin = adminRepo.findAdminById(extractToken);
        if (admin == null) {
            throw new RuntimeException("NO ADMİN FOUNDED");
        }
        // KİRALAMAYI PENALTY YAPALIM
        int penaltyRental =  rentalRepo.updateStatus(RentalStatus.PENALTY,rentalId);
        if (penaltyRental == 0) {
            throw new RuntimeException("Can't PENALTY approve");
        }
    }

    public List<ApproveDto> showCancel(String token) {
        UUID extractToken = jwtService.extractUserId(token);
        AdminTable admin = adminRepo.findAdminById(extractToken);
        if (admin == null) {
            throw new RuntimeException("NO ADMİN FOUNDED");
        }
        List<RentalTable> cancelRental = rentalRepo.findByRentalStatusEnum(RentalStatus.CANCEL_APPROVE);
        if(cancelRental.isEmpty()){
            throw new RuntimeException("NO FOUNDED");
        }
        return getApproveList(cancelRental);

    }

}
