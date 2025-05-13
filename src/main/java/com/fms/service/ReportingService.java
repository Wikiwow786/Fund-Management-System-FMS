package com.fms.service;


import com.fms.models.BankSummaryReportModel;
import com.fms.models.CommissionSummaryReportModel;
import com.fms.models.CustomerSummaryReportModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ReportingService {

    Page<BankSummaryReportModel> getBankSummaryReport(Long bankId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable);

    Page<CustomerSummaryReportModel> getCustomerSummaryReport(Long customerId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable);

    Page<CommissionSummaryReportModel> getCommissionSummaryReport(LocalDate dateFrom, LocalDate dateTo,LocalDate summaryDate,String revenueAccount, Pageable pageable);
}
