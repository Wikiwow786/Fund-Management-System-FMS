
package com.fms.fund_management_system.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(schema ="fms", name="permission")
@Data
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "permission_code")
    private String permissionCode;

    @Column(name = "description")
    private String description;
}

