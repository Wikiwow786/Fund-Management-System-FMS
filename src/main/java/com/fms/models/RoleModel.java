package com.fms.models;


import com.fms.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class RoleModel {

    private Long roleId;
    private String roleName;
    private Role.RoleStatus status;
    private List<RolePermissionModel> rolePermissionModels;

    public RoleModel(Role role){
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
        this.status = role.getStatus();
        this.rolePermissionModels = !ObjectUtils.isEmpty(role.getRolePermissions()) ? role.getRolePermissions().stream().map(RolePermissionModel::new).collect(Collectors.toList())
                : null;

    }
}
