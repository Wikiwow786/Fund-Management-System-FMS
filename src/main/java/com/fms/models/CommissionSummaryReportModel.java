package com.fms.models;

import com.fms.entities.CommissionSummaryReport;
import com.fms.entities.CustomerSummaryReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommissionSummaryReportModel {

    private Long transactionId;
    private LocalDate summaryDate;

    private LocalDateTime transactionDateTime;
    private String revenueAccount;
    private BigDecimal fundIn;
    private BigDecimal fundOut;
    private BigDecimal claimed;
    private BigDecimal balance;

    private BigDecimal commissionAmount;
    private String createdBy;

    public CommissionSummaryReportModel(CommissionSummaryReport commissionSummaryReport){
        this.transactionId = commissionSummaryReport.getTransactionId();
        this.summaryDate = commissionSummaryReport.getSummaryDate();
        this.transactionDateTime = commissionSummaryReport.getTransactionDatetime();
        this.revenueAccount = commissionSummaryReport.getRevenueAccount();
        this.balance = commissionSummaryReport.getBalance();
        this.commissionAmount = commissionSummaryReport.getCommissionAmount();
        this.fundIn = commissionSummaryReport.getFundIn();
        this.fundOut = commissionSummaryReport.getFundOut();
        this.claimed = commissionSummaryReport.getClaimed();
        this.createdBy = commissionSummaryReport.getCreatedBy();
    }

    public CommissionSummaryReportModel(CustomerSummaryReport customerSummaryReport) {
    }
}
