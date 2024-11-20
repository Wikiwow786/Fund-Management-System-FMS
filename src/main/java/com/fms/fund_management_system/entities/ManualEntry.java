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
@Table(name = "fms.manual_entry", schema = "fms")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ManualEntry extends BaseEntity{

   // private volatile boolean updated = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manualEntryId;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false, foreignKey = @ForeignKey(name = "fk_manual_entry_bank"))
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_manual_entry_customer"))
    private Customer customer;

    @Column(name = "entry_date", nullable = false)
    private Date entryDate;

    @Column(name = "entry_time", nullable = false)
    private Time entryTime;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "entry_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ManualEntryType entryType;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ManualEntryStatus status;

    @Column(name = "remark")
    private String remark;

  /*  @ManyToOne
    @JoinColumn(name = "created_by", foreignKey = @ForeignKey(name = "fk_manual_entry_created_by"))
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by", foreignKey = @ForeignKey(name = "fk_manual_entry_updated_by"))
    private User updatedBy;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;*/

   /* @PrePersist
    public void onCreation() {
        this.setCreatedAt(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
    }

    @PreUpdate
    public void onUpdate() {
        if (this.updated) {
            this.setUpdatedAt(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        }
    }*/
    public enum ManualEntryType {
        BANK_INTEREST, EXPENSES, OTHERS
    }

    public enum ManualEntryStatus {
        VALID, VOIDED
    }
}

