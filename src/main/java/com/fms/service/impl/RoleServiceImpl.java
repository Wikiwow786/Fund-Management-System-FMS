package com.fms.service.impl;

import com.fms.entities.Permission;
import com.fms.entities.QRole;
import com.fms.entities.Role;
import com.fms.entities.RolePermission;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.RoleMapper;
import com.fms.models.RoleModel;
import com.fms.models.RolePermissionModel;
import com.fms.repositories.PermissionRepository;
import com.fms.repositories.RolePermissionRepository;
import com.fms.repositories.RoleRepository;
import com.fms.service.RoleService;
import com.querydsl.core.BooleanBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public RoleModel getRole(Long roleId) {
        return new RoleModel(roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<RoleModel> getAllRoles(String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();

        if (StringUtils.isNotBlank(search)) {
            filter.and(QRole.role.roleName.equalsIgnoreCase(search));
        }
        return roleRepository.findAll(filter, pageable).map(RoleModel::new);
    }

    @Override
    public RoleModel createOrUpdate(RoleModel roleModel, Long roleId) {
        Role role = roleRepository.save(assemble(roleModel, roleId));
        if (roleId != null) {
            updateRolePermissions(role,roleModel);
        } else {
            createRolePermissions(role);
        }
        return new RoleModel(role);
    }

    public Role assemble(RoleModel roleModel, Long roleId) {

        Role role;
        if (null != roleId) {
            role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        } else {
            role = new Role();
        }
        roleMapper.toEntity(roleModel, role);
        return role;
    }

    private void createRolePermissions(Role role) {
        List<Permission> permissions = permissionRepository.findAll();
        permissions.forEach(permission -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(permission);
            rolePermission.setRole(role);
            rolePermissionRepository.save(rolePermission);
        });
    }

    @Transactional
    private void updateRolePermissions(Role role, RoleModel roleModel) {
        List<RolePermission> existingPermissions = rolePermissionRepository.findAllByRole(role);
        Set<Long> existingPermissionIds = existingPermissions.stream()
                .map(rp -> rp.getPermission().getPermissionId())
                .collect(Collectors.toSet());
        Set<Long> newPermissionIds = roleModel.getRolePermissionModels().stream()
                .map(RolePermissionModel::getPermissionId)
                .collect(Collectors.toSet());
        List<RolePermission> permissionsToRemove = existingPermissions.stream()
                .filter(rp -> !newPermissionIds.contains(rp.getPermission().getPermissionId()))
                .collect(Collectors.toList());
        rolePermissionRepository.deleteAll(permissionsToRemove);
        List<Long> permissionsToAdd = newPermissionIds.stream()
                .filter(permissionId -> !existingPermissionIds.contains(permissionId)).toList();
        permissionsToAdd.forEach(permissionId -> {
            RolePermission newRolePermission = new RolePermission();
            newRolePermission.setRole(role);
            newRolePermission.setPermission(permissionRepository.findById(permissionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Permission not found")));
            rolePermissionRepository.save(newRolePermission);
        });
    }

}
