package com.fms.repositories;

import com.fms.entities.Role;
import com.fms.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> {
    List<RolePermission> findAllByRole(Role role);
}
