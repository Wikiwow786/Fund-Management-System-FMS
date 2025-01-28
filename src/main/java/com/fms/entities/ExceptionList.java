package com.fms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "exception_list", schema = "fms")
@Getter
@Setter
public class ExceptionList{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exception_id")
    private Long exceptionId;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @Column(name="cause")
    private String cause;

    @Column(name = "solution")
    private String solution;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name="status")
    private ExceptionStatus status;

    @Column(name="system_balance")
    private BigDecimal systemBalance;

    @Column(name="input_balance")
    private BigDecimal inputBalance;

    @Column(name="imbalance_amount")
    private BigDecimal imbalanceAmount;

    public enum ExceptionStatus{
        EXPLAINED,
        UNEXPLAINED
    }

}
