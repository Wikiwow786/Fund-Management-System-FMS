
package com.fms.fund_management_system.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(schema ="fms", name="user")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity{

   // private volatile boolean updated = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "status")
    private String status;

  /*  @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime updateAt;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;*/

    public enum BankStatus {
        ACTIVE,
        INACTIVE
    }
   /* @PrePersist
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

