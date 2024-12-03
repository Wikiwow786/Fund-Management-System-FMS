package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.Permission;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionModel {

    private Long permissionId;
    private String permissionCode;

    public PermissionModel(Permission permission) {
        this.permissionId = permission.getPermissionId();
        this.permissionCode = permission.getPermissionCode();

    }
}
