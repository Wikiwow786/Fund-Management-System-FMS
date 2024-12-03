package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String name);
    User findUserByEmailIgnoreCase(String email);
}
