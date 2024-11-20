
package com.fms.fund_management_system.entities;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(schema ="fms", name = "bank")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Bank extends BaseEntity{

   // private volatile boolean updated = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bank_id")
    private Long bankId;

    @Column(name="bank_name")
    private String bankName;

    @Column(name="balance")
    private Double balance;

    @Column(name="balance_limit")
    private Double balanceLimit;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private BankStatus status;

   /* @Column(name = "updated_at")
    private OffsetDateTime updateAt;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;*/

    @Column(name="remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    public enum BankStatus {
        ACTIVE,
        INACTIVE
    }
/*    @PrePersist
    public void onCreation() {
        this.setCreatedAt(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
    }

    @PreUpdate
    public void onUpdate() {
        if (this.updated) {
            this.setUpdateAt(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        }
    }*/
}

