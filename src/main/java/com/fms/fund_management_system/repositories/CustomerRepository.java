package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>, QuerydslPredicateExecutor<Customer> {
    List<Customer> findAllByCustomerIdIn(List<Long> customerIds);
}
