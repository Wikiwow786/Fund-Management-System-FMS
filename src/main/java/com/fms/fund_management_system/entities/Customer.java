
package com.fms.fund_management_system.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
@Entity
@Table(schema ="fms", name="customer")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity{

   // private volatile boolean updated = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "status")
    private String status;

    @Column(name = "fund_in_fee_percentage")
    private Double fundInFeePct;

    @Column(name = "fund_out_fee_percentage")
    private Double fundOutFeePct;

    @Column(name = "commission_in_percentage")
    private Double commissionInPct;

    @Column(name = "commission_out_percentage")
    private Double commissionOutPct;

 /*   @Column(name = "updated_at")
    private OffsetDateTime updateAt;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;*/

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "middleman_id")
    private User user;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "sourceCustomer")
    private List<BalanceTransfer> outgoingTransfers;

    @OneToMany(mappedBy = "targetCustomer")
    private List<BalanceTransfer> incomingTransfers;

   /* @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;*/


  /*  @PrePersist
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

