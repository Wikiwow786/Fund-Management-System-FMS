package com.fms.fund_management_system.models;


import com.fms.fund_management_system.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class RoleModel {

    private Long roleId;
    private String roleName;
    private Role.RoleStatus status;

    public RoleModel(Role role){
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
        this.status = role.getStatus();

    }
}
