package com.fms.service.impl;

import com.fms.entities.QBankSummaryReport;
import com.fms.entities.QCommissionSummaryReport;
import com.fms.entities.QCustomerSummaryReport;
import com.fms.models.BankSummaryReportModel;
import com.fms.models.CommissionSummaryReportModel;
import com.fms.models.CustomerSummaryReportModel;
import com.fms.repositories.BankSummaryReportRepository;
import com.fms.repositories.CommissionSummaryReportRepository;
import com.fms.repositories.CustomerSummaryReportRepository;
import com.fms.service.ReportingService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class ReportingServiceImpl implements ReportingService {
    private final BankSummaryReportRepository bankSummaryReportRepository;
    private final CustomerSummaryReportRepository customerSummaryReportRepository;
    private final CommissionSummaryReportRepository commissionSummaryReportRepository;
    @Override
    public Page<BankSummaryReportModel> getBankSummaryReport(Long bankId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(bankId != null){
           filter.and(QBankSummaryReport.bankSummaryReport.bankId.eq(bankId));
        }
        if(!ObjectUtils.isEmpty(dateFrom)){
            filter.and(QBankSummaryReport.bankSummaryReport.transactionDateTime.goe(dateFrom.atStartOfDay()));
        }
        if(!ObjectUtils.isEmpty(dateFrom)){
            filter.and(QBankSummaryReport.bankSummaryReport.transactionDateTime.loe(dateTo.atTime(23,59,59)));
        }
        return bankSummaryReportRepository.findAll(filter,pageable).map(BankSummaryReportModel::new);
    }

    @Override
    public Page<CustomerSummaryReportModel> getCustomerSummaryReport(Long customerId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
       if(customerId != null){
            filter.and(QCustomerSummaryReport.customerSummaryReport.customerId.eq(customerId));
        }
        if(!ObjectUtils.isEmpty(dateFrom)){
            filter.and(QCustomerSummaryReport.customerSummaryReport.transactionDateTime.goe(dateFrom.atStartOfDay()));
        }
        if(!ObjectUtils.isEmpty(dateFrom)){
            filter.and(QCustomerSummaryReport.customerSummaryReport.transactionDateTime.loe(dateTo.atTime(23,59,59)));
        }
        return customerSummaryReportRepository.findAll(filter,pageable).map(CustomerSummaryReportModel::new);

    }

    @Override
    public Page<CommissionSummaryReportModel> getCommissionSummaryReport(LocalDate dateFrom, LocalDate dateTo,LocalDate summaryDate,String revenueAccount, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(revenueAccount)){
            filter.and(QCommissionSummaryReport.commissionSummaryReport.revenueAccount.containsIgnoreCase(revenueAccount));

        }
        if(!ObjectUtils.isEmpty(dateFrom)){
            filter.and(QCommissionSummaryReport.commissionSummaryReport.summaryDate.goe(dateFrom));
        }
        if(!ObjectUtils.isEmpty(dateFrom)){
            filter.and(QCommissionSummaryReport.commissionSummaryReport.summaryDate.loe(dateTo));
        }
        if(!ObjectUtils.isEmpty(summaryDate)){
            filter.and(QCommissionSummaryReport.commissionSummaryReport.summaryDate.eq(summaryDate));
        }
        return commissionSummaryReportRepository.findAll(filter,pageable).map(CommissionSummaryReportModel::new);
    }
}
