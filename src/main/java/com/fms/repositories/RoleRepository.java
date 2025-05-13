package com.fms.repositories;

import com.fms.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RoleRepository extends JpaRepository<Role,Long>, QuerydslPredicateExecutor<Role> {
    Role findByRoleNameIgnoreCase(String roleName);
}
