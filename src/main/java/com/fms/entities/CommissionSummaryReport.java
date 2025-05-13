package com.fms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "commission_summary_report", schema = "fms")
@Getter
@Setter
@ToString
public class CommissionSummaryReport {

    @Id
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "transaction_datetime")
    private LocalDateTime transactionDatetime;

    @Column(name = "summary_date")
    private LocalDate summaryDate;

    @Column(name = "revenue_account")
    private String revenueAccount;

    @Column(name = "fund_in")
    private BigDecimal fundIn;

    @Column(name = "fund_out")
    private BigDecimal fundOut;

    @Column(name = "claimed")
    private BigDecimal claimed;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "commission_amount")
    private BigDecimal commissionAmount;

    @Column(name = "created_by")
    private String createdBy;
}
