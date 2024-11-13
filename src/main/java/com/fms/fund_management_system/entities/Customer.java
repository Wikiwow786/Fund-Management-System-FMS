
package com.fms.fund_management_system.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(schema ="fms", name="customer")
@Data
public class Customer {

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
    private Double fundInFeePercentage;

    @Column(name = "fund_out_fee_percentage")
    private Double fundOutFeePercentage;

    @Column(name = "commission_in_percentage")
    private Double commissionInPercentage;

    @Column(name = "commission_out_percentage")
    private Double commissionOutPercentage;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

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

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}

