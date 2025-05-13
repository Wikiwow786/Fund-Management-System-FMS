package com.fms.models;

import com.fms.entities.BankSummaryReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BankSummaryReportModel {
    private Long bankId;
    private Long transactionId;
    private String bankName;
    private String createdBy;

    private LocalDateTime transactionDateTime;
    private BigDecimal fundIn;
    private BigDecimal fundOut;
    private BigDecimal balanceTransferIn;
    private BigDecimal balanceTransferOut;
    private BigDecimal unclaimedAmount;
    private BigDecimal claimedAmount;
    private BigDecimal manualEntry;
    private BigDecimal currentBalance;
    private BigDecimal total;

    public BankSummaryReportModel(BankSummaryReport bankSummaryReport){
        this.bankId = bankSummaryReport.getBankId();
        this.bankName = bankSummaryReport.getBankName();
        this.createdBy = bankSummaryReport.getCreatedBy();
        this.transactionId = bankSummaryReport.getTransactionId();
        this.transactionDateTime = bankSummaryReport.getTransactionDateTime();
        this.fundIn = bankSummaryReport.getFundIn();
        this.fundOut = bankSummaryReport.getFundOut();
        this.balanceTransferIn = bankSummaryReport.getBalanceTransferIn();
        this.balanceTransferOut = bankSummaryReport.getBalanceTransferOut();
        this.unclaimedAmount = bankSummaryReport.getUnclaimedAmount();
        this.claimedAmount = bankSummaryReport.getClaimedAmount();
        this.manualEntry = bankSummaryReport.getManualEntry();
        this.currentBalance = bankSummaryReport.getCurrentBalance();
        this.total = bankSummaryReport.getTotal();
    }
}
