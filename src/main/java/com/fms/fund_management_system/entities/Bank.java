
package com.fms.fund_management_system.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(schema ="fms", name = "bank")
@Getter
@Setter
public class Bank {

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

    @Column(name="status")
    private String status;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @Column(name="remarks")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}

