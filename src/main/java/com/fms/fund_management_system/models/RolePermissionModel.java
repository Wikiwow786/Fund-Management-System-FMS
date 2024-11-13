package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.RolePermission;
import lombok.Data;

@Data
public class RolePermissionModel {

    private Long rolePermissionId;
    private Long roleId;
    private Long permissionId;

    public RolePermissionModel(RolePermission rolePermission) {
        this.rolePermissionId = rolePermission.getRolePermissionId();
        this.roleId = rolePermission.getRole() != null ? rolePermission.getRole().getRoleId() : null;
        this.permissionId = rolePermission.getPermission() != null ? rolePermission.getPermission().getPermissionId() : null;
    }
}
