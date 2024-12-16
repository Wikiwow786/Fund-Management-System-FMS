package com.fms.service;

import com.fms.models.RoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    
    RoleModel getRole(Long roleId);

    Page<RoleModel> getAllRoles(String search, Pageable pageable);

    RoleModel createOrUpdate(RoleModel roleModel, Long roleId);
}
