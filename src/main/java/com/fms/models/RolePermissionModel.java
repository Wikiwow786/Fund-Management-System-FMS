package com.fms.models;

import com.fms.entities.RolePermission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RolePermissionModel {

    private Long rolePermissionId;
    private Long roleId;
    private Long permissionId;
    private String permissionName;

    public RolePermissionModel(RolePermission rolePermission) {
        this.rolePermissionId = rolePermission.getRolePermissionId();
        this.roleId = rolePermission.getRole() != null ? rolePermission.getRole().getRoleId() : null;
        this.permissionId = rolePermission.getPermission() != null ? rolePermission.getPermission().getPermissionId() : null;
        this.permissionName = rolePermission.getPermission() != null ? rolePermission.getPermission().getPermissionCode() : null;
    }
}
