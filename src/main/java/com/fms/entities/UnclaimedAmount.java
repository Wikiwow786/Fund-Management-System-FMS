
package com.fms.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(schema ="fms", name = "unclaimed_amount")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UnclaimedAmount extends BaseEntity{

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
    @JoinColumn(name = "claim_as_transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "remark")
    private String remark;

    @Column(name = "void_remark")
    private String voidRemark;

    @ManyToOne
    @JoinColumn(name = "claimed_by")
    private Customer claimedBy;

    public enum UnclaimedAmountStatus {
        UNCLAIMED, CLAIMED, VOIDED
    }
}


