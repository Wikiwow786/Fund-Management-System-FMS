package com.fms.models;
import com.fms.entities.CustomerSummaryReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CustomerSummaryReportModel {

    private Long customerId;
    private Long transactionId;
    private String customerName;
    private String createdBy;
    private LocalDateTime transactionDateTime;
    private BigDecimal totalFundIn;
    private BigDecimal totalFundOut;
    private BigDecimal balanceTransferIn;
    private BigDecimal balanceTransferOut;
    private BigDecimal claimedAmount;
    private BigDecimal total;
    private BigDecimal totalFee;
    private BigDecimal totalCommission;
    private BigDecimal currentBalance;

    public CustomerSummaryReportModel(CustomerSummaryReport customerSummaryReport) {
        this.customerId = customerSummaryReport.getCustomerId();
        this.transactionId = customerSummaryReport.getTransactionId();
        this.createdBy = customerSummaryReport.getCreatedBy();
        this.customerName = customerSummaryReport.getCustomerName();
        this.transactionDateTime = customerSummaryReport.getTransactionDateTime();
        this.totalFundIn = customerSummaryReport.getTotalFundIn();
        this.totalFundOut = customerSummaryReport.getTotalFundOut();
        this.balanceTransferIn = customerSummaryReport.getBalanceTransferIn();
        this.balanceTransferOut = customerSummaryReport.getBalanceTransferOut();
        this.claimedAmount = customerSummaryReport.getClaimedAmount();
        this.total = customerSummaryReport.getTotal();
        this.totalFee = customerSummaryReport.getTotalFee();
        this.totalCommission = customerSummaryReport.getTotalCommission();
        this.currentBalance = customerSummaryReport.getCurrentBalance();
    }
}
