package com.fms.models;

import com.fms.entities.Permission;
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
