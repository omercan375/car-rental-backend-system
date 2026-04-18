package com.example.rentcar.adminApprove.cancelApprove;

import com.example.rentcar.adminApprove.ApproveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/approve/cancel")
public class CancelApproveController {
    @Autowired
    private CancelApproveService cancelApproveService;


    @GetMapping("/show-cancel")
    public ResponseEntity<?> showCancel(@RequestHeader("Authorization") String token) {
        List<ApproveDto> cancels = cancelApproveService.showCancel(token);
        return ResponseEntity.ok().body(cancels);
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approveCancel(@RequestHeader("Authorization") String token, @RequestParam int rentalId) {
        cancelApproveService.approveCancel(token, rentalId);
        return ResponseEntity.ok().build();
    }
}
