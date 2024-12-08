
package com.fms.entities;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema ="fms", name = "bank")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Bank extends BaseEntity{



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

    @Column(name="remarks")
    private String remarks;


    public enum BankStatus {
        ACTIVE,
        INACTIVE
    }

}

