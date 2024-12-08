package com.fms.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "fms.manual_entry", schema = "fms")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ManualEntry extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manualEntryId;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false, foreignKey = @ForeignKey(name = "fk_manual_entry_bank"))
    private Bank bank;

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

    public enum ManualEntryType {
        BANK_INTEREST, EXPENSES, OTHERS
    }

    public enum ManualEntryStatus {
        VALID, VOIDED
    }
}

