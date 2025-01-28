package com.fms.models;

import com.fms.entities.BankSummaryReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BankSummaryReportModel {
    private Long bankId;
    private String bankName;

    private LocalDate summaryDate;
    private BigDecimal fundIn;
    private BigDecimal fundOut;
    private BigDecimal balanceTransferIn;
    private BigDecimal balanceTransferOut;
    private BigDecimal unclaimedAmount;
    private BigDecimal claimedAmount;
    private BigDecimal manualEntry;
    private BigDecimal currentBalance;

    public BankSummaryReportModel(BankSummaryReport bankSummaryReport){
        this.bankId = bankSummaryReport.getBankId();
        this.bankName = bankSummaryReport.getBankName();
        this.summaryDate = bankSummaryReport.getSummaryDate();
        this.fundIn = bankSummaryReport.getFundIn();
        this.fundOut = bankSummaryReport.getFundOut();
        this.balanceTransferIn = bankSummaryReport.getBalanceTransferIn();
        this.balanceTransferOut = bankSummaryReport.getBalanceTransferOut();
        this.unclaimedAmount = bankSummaryReport.getUnclaimedAmount();
        this.claimedAmount = bankSummaryReport.getClaimedAmount();
        this.manualEntry = bankSummaryReport.getManualEntry();
        this.currentBalance = bankSummaryReport.getCurrentBalance();

    }
}
