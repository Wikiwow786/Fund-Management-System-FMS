package com.fms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "commission_summary_report", schema = "fms")
@Getter
@Setter
@ToString
public class CommissionSummaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name = "summary_datetime")
    private LocalDateTime summaryDatetime;

    @Column(name = "revenue_account")
    private String revenueAccount;

    @Column(name = "commission_amount")
    private BigDecimal commissionAmount;
}
