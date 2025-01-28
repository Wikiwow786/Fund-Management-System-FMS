package com.fms.models;

import com.fms.entities.CommissionSummaryReport;
import com.fms.entities.CustomerSummaryReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommissionSummaryReportModel {

    private Long id;
    private LocalDateTime summaryDate;
    private String revenueAccount;
    private BigDecimal commission;

    public CommissionSummaryReportModel(CommissionSummaryReport commissionSummaryReport){
        this.id = commissionSummaryReport.getId();
        this.summaryDate = commissionSummaryReport.getSummaryDatetime();
        this.revenueAccount = commissionSummaryReport.getRevenueAccount();
        this.commission = commissionSummaryReport.getCommissionAmount();
    }

    public CommissionSummaryReportModel(CustomerSummaryReport customerSummaryReport) {
    }
}
