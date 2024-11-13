package com.fms.fund_management_system.models;


import com.fms.fund_management_system.entities.Role;
import lombok.Data;



@Data
public class RoleModel {

    private Long roleId;
    private String roleName;
    private String status;

    public RoleModel(Role role){
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
        this.status = role.getStatus();

    }
}
