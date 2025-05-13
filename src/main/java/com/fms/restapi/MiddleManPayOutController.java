package com.fms.restapi;

import com.fms.models.MiddleManPayOutModel;
import com.fms.security.SecurityUser;
import com.fms.service.MiddleManPayOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RequestMapping(value = "/payout")
@RestController
@RequiredArgsConstructor
public class MiddleManPayOutController {
    private final MiddleManPayOutService middleManPayOutService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MiddleManPayOutModel>> fetchPayOuts(@RequestParam(required = false)String revenueAccount, @RequestParam(required = false)String status, @RequestParam(required = false)@DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate, @AuthenticationPrincipal SecurityUser securityUser, Pageable pageable) {
       return ResponseEntity.ok(middleManPayOutService.getAllPayOuts(revenueAccount, status, startDate,pageable));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> handlePayOut(@RequestBody MiddleManPayOutModel middleManPayOutModel, @AuthenticationPrincipal SecurityUser securityUser) {
        middleManPayOutService.handleMiddleManPayOut(middleManPayOutModel,securityUser);
        return ResponseEntity.ok("PayOut successful");
    }
}
