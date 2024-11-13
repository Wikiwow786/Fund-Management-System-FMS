
package com.fms.fund_management_system.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(schema ="fms", name = "unclaimed_amounts")
@Data
public class UnclaimedAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unclaimedId;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "transaction_time", nullable = false)
    private Time transactionTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UnclaimedAmountStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "remark")
    private String remark;

    @Column(name = "void_remark")
    private String voidRemark;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "claimed_by")
    private User claimedBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum UnclaimedAmountStatus {
        UNCLAIMED, CLAIMED, VOIDED
    }
}


