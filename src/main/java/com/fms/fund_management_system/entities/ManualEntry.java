package com.fms.fund_management_system.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "fms.manual_entry", schema = "fms")
public class ManualEntry {

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

    @ManyToOne
    @JoinColumn(name = "created_by", foreignKey = @ForeignKey(name = "fk_manual_entry_created_by"))
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by", foreignKey = @ForeignKey(name = "fk_manual_entry_updated_by"))
    private User updatedBy;

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

    public enum ManualEntryType {
        BANK_INTEREST, ADJUSTMENT, CORRECTION
    }

    public enum ManualEntryStatus {
        COMPLETED, VOIDED
    }
}

