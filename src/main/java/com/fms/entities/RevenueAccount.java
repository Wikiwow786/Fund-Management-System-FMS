
package com.fms.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RevenueAccountStatus status;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name = "remarks")
    private String remarks;

    public enum RevenueAccountStatus{
        ACTIVE,
        INACTIVE
    }
}

