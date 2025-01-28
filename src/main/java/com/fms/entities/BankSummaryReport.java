package com.fms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(schema = "fms", name = "bank_summary_report")
@Getter
@Setter
public class BankSummaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bank_id")
    private Long bankId;

    @Column(name="bank_name")
    private String bankName;

    @Column(name="summary_date")
    private LocalDate summaryDate;

    @Column(name="fund_in")
    private BigDecimal fundIn;

    @Column(name="fund_out")
    private BigDecimal fundOut;

    @Column(name="balance_transfer_in")
    private BigDecimal balanceTransferIn;

    @Column(name="balance_transfer_out")
    private BigDecimal balanceTransferOut;

    @Column(name="unclaimed_amount")
    private BigDecimal unclaimedAmount;

    @Column(name="claimed_amount")
    private BigDecimal claimedAmount;

    @Column(name="manual_entry")
    private BigDecimal manualEntry;

    @Column(name="current_balance")
    private BigDecimal currentBalance;
}
