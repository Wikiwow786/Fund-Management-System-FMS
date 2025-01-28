package com.fms.restapi;


import com.fms.models.BankSummaryReportModel;
import com.fms.models.CommissionSummaryReportModel;
import com.fms.models.CustomerSummaryReportModel;
import com.fms.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RequestMapping(value = "/reporting")
@RestController
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @GetMapping(value = "/bank-summary",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BankSummaryReportModel>> fetchBankSummaryReport(@RequestParam(required = false) Long bankId,
                                                           @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
                                                           @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,
                                                           Pageable pageable) {


       return ResponseEntity.ok(reportingService.getBankSummaryReport(bankId, dateFrom, dateTo, pageable));
    }

    @GetMapping(value = "/customer-summary",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CustomerSummaryReportModel>> fetchCustomerSummaryReport(@RequestParam(required = false) Long customerId,
                                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
                                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,
                                                                     Pageable pageable) {


        return ResponseEntity.ok(reportingService.getCustomerSummaryReport(customerId, dateFrom, dateTo, pageable));
    }

    @GetMapping(value = "/commission-summary",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CommissionSummaryReportModel>> fetchCommissionSummaryReport(@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
                                                                                           @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,
                                                                                           Pageable pageable) {


        return ResponseEntity.ok(reportingService.getCommissionSummaryReport(dateFrom, dateTo, pageable));
    }
}
