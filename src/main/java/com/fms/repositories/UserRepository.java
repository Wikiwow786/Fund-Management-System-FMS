package com.fms.repositories;

import com.fms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User,Long>, QuerydslPredicateExecutor<User> {
    User findByNameIgnoreCase(String name);
   // User findUserByEmailIgnoreCase(String email);
}
