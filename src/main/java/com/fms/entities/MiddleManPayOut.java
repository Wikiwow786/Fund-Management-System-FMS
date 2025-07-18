package com.fms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(schema ="fms", name = "middleman_payout")
@Getter
@Setter
public class MiddleManPayOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payout_id")
    private Long payoutId;

    @ManyToOne
    @JoinColumn(name = "revenue_account_id", nullable = false)
    private RevenueAccount revenueAccount;

    @Column(name = "payout_amount", nullable = false)
    private BigDecimal payoutAmount;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;


    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

}
