package com.fms.repositories;

import com.fms.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long>, QuerydslPredicateExecutor<Customer> {
    List<Customer> findAllByCustomerIdIn(List<Long> customerIds);
}
