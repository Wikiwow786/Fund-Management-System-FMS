
package com.fms.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema ="fms", name="role")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RoleStatus status;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    @OneToMany(mappedBy = "role")
    private List<RolePermission> rolePermissions;

    public enum RoleStatus{
        ACTIVE,
        INACTIVE

    }
}

