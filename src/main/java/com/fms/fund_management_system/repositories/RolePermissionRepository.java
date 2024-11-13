package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> {
}
