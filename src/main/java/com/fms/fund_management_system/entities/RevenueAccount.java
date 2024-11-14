
package com.fms.fund_management_system.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(schema ="fms", name = "revenue_account")
@Getter
@Setter
public class RevenueAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revenue_account_id")
    private Long revenueAccountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "status")
    private String status;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @Column(name = "remarks")
    private String remarks;
}

