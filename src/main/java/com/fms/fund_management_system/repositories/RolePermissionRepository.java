package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.Role;
import com.fms.fund_management_system.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> {
    List<RolePermission> findAllByRole(Role role);
}
