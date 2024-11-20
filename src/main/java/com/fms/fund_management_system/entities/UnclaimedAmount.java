
package com.fms.fund_management_system.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(schema ="fms", name = "unclaimed_amounts")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UnclaimedAmount extends BaseEntity{

   // private volatile boolean updated = true;

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
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Column(name = "remark")
    private String remark;

    @Column(name = "void_remark")
    private String voidRemark;

   /* @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;*/

    @ManyToOne
    @JoinColumn(name = "claimed_by")
    private User claimedBy;

  /*  @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime updatedAt;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime createdAt;

    @PrePersist
    public void onCreation() {
        this.setCreatedAt(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
    }

    @PreUpdate
    public void onUpdate() {
        if (this.updated) {
            this.setUpdatedAt(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        }
    }*/

    public enum UnclaimedAmountStatus {
        UNCLAIMED, CLAIMED, VOIDED
    }
}


