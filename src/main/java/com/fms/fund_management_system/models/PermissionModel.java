package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionModel {

    private Long permissionId;
    private String permissionCode;
    private String description;

    // Constructor that takes a Permission entity and initializes the model
    public PermissionModel(Permission permission) {
        this.permissionId = permission.getPermissionId();
        this.permissionCode = permission.getPermissionCode();
        this.description = permission.getDescription();
    }
}
