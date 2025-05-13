package com.fms.restapi;


import com.fms.models.BankSummaryReportModel;
import com.fms.models.CommissionSummaryReportModel;
import com.fms.models.CustomerSummaryReportModel;
import com.fms.service.ReportingService;
import com.fms.service.impl.BankSummaryExcelExporter;
import com.fms.service.impl.CommissionSummaryExcelExporter;
import com.fms.service.impl.CustomerSummaryExcelExporter;
import com.fms.service.impl.GenericExcelExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequestMapping(value = "/reporting")
@RestController
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;
    private final GenericExcelExportService genericExcelExportService;
    private final CustomerSummaryExcelExporter customerSummaryExcelExporter;
    private final BankSummaryExcelExporter bankSummaryExcelExporter;
    private final CommissionSummaryExcelExporter commissionSummaryExcelExporter;

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
                                                                                           @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate summaryDate,
                                                                                           @RequestParam(required = false) String revenueAccount,
                                                                                           Pageable pageable) {


        return ResponseEntity.ok(reportingService.getCommissionSummaryReport(dateFrom, dateTo,summaryDate,revenueAccount, pageable));
    }

    @GetMapping("/customer-summary/download")
    public ResponseEntity<InputStreamResource> downloadCustomerSummary(
            @RequestParam Long customerId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,Pageable pageable
    ) throws IOException {
        List<CustomerSummaryReportModel> data = reportingService.getCustomerSummaryReport(customerId, dateFrom, dateTo,pageable).stream().toList();
        ByteArrayInputStream stream = genericExcelExportService.export(data, customerSummaryExcelExporter);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=customer_summary.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }

    @GetMapping("/bank-summary/download")
    public ResponseEntity<InputStreamResource> downloadBankSummary(
            @RequestParam Long bankId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,Pageable pageable
    ) throws IOException {
        List<BankSummaryReportModel> data = reportingService.getBankSummaryReport(bankId, dateFrom, dateTo,pageable).stream().toList();
        ByteArrayInputStream stream = genericExcelExportService.export(data, bankSummaryExcelExporter);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bank_summary.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }


    @GetMapping("/commission-summary/download")
    public ResponseEntity<InputStreamResource> downloadCommissionSummary(
            @RequestParam String revenueAccount,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate summaryDate,Pageable pageable
    ) throws IOException {
        List<CommissionSummaryReportModel> data = reportingService.getCommissionSummaryReport(dateFrom, dateTo,summaryDate,revenueAccount,pageable).stream().toList();
        ByteArrayInputStream stream = genericExcelExportService.export(data, commissionSummaryExcelExporter);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=commission_summary.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }



}
