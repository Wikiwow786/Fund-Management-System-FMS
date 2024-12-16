package com.fms.repositories;

import com.fms.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PermissionRepository extends JpaRepository<Permission,Long>, QuerydslPredicateExecutor<Permission> {
}
