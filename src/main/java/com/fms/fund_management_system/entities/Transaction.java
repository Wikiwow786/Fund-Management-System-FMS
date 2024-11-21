
package com.fms.fund_management_system.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(schema ="fms", name="transaction")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "transaction_time", nullable = false)
    private Time transactionTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "external_id")
    private String externalId;
    public enum TransactionType {
        FUND_IN, FUND_OUT
    }

    public enum TransactionStatus {
        COMPLETED, VOIDED
    }
}

