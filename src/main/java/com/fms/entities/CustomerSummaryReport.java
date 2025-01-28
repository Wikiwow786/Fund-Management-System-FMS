package com.fms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_summary_report", schema = "fms")
@Getter
@Setter
@ToString
public class CustomerSummaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "summary_date")
    private LocalDateTime summaryDate;

    @Column(name = "total_fund_in")
    private BigDecimal totalFundIn;

    @Column(name = "total_fund_out")
    private BigDecimal totalFundOut;

    @Column(name = "balance_transfer_in")
    private BigDecimal balanceTransferIn;

    @Column(name = "balance_transfer_out")
    private BigDecimal balanceTransferOut;

    @Column(name = "claimed_amount")
    private BigDecimal claimedAmount;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "total_fee")
    private BigDecimal totalFee;

    @Column(name = "total_commission")
    private BigDecimal totalCommission;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

}
