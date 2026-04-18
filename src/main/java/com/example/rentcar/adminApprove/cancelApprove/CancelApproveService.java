package com.example.rentcar.adminApprove.cancelApprove;

import com.example.rentcar.admin.AdminRepo;
import com.example.rentcar.admin.AdminTable;
import com.example.rentcar.adminApprove.ApproveDto;
import com.example.rentcar.adminApprove.ApproveService;
import com.example.rentcar.cars.CarStatu;
import com.example.rentcar.cars.CarsRepo;
import com.example.rentcar.payment.PaymentService;
import com.example.rentcar.rental.RentalRepo;
import com.example.rentcar.rental.RentalService;
import com.example.rentcar.rental.RentalStatus;
import com.example.rentcar.rental.RentalTable;
import com.example.rentcar.zcommon.JwtService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.naming.NamingEnumeration;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class CancelApproveService {
@Autowired
private RentalRepo rentalRepo;
@Autowired
private JwtService jwtService;
@Autowired
private AdminRepo adminRepo;
@Autowired
private ApproveService approveService;
    @Autowired
    private CarsRepo carsRepo;
    @Autowired
    private PaymentService paymentService;


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
        return approveService.getApproveList(cancelRental);

    }

    @Transactional
    public void approveCancel(String token,int rentalId) {
        UUID extractToken = jwtService.extractUserId(token);
        AdminTable admin = adminRepo.findAdminById(extractToken);
        if (admin == null) {
            throw new RuntimeException("NO ADMİN FOUNDED");
        }
        // kiralamatı completed yapalım
        int updateStatus = rentalRepo.updateStatus(RentalStatus.COMPLETED, rentalId);
        if(updateStatus == 0){
            throw new RuntimeException("UPDATE FAILED TO RENTAL COMPLETED");
        }

        // kiralama bilgilerini  bulalım
        RentalTable rentalTable = rentalRepo.findOne(rentalId);
        // ARABAYI AKTİF HALE GETİRELİM
        int updateCar = carsRepo.makeCarFree(CarStatu.AVAILABLE,rentalTable.getCar().getId());
        if(updateCar == 0){
            throw new RuntimeException("UPDATE FAILED TO CAR AVAILABLE");
        }
        // KAÇ GÜN KALDIĞINI BULALIM
        long days = ChronoUnit.DAYS.between(LocalDateTime.now(), rentalTable.getEndDate());
        if (days <= 0) {
            throw new RuntimeException("DATE PROBLEM");
        }
        // NE KADAR İADE EDİLECEĞİNİ HESAPLAYALIM
        BigDecimal returnAmount = rentalTable.getDailyPrice().multiply(new BigDecimal(days));

        boolean returnPayment = paymentService.returnPay(returnAmount,rentalTable.getUser().getUserId());
        if(!returnPayment){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

}
