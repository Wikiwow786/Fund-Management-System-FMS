package com.fms.fund_management_system.service;

import com.fms.fund_management_system.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface CustomerService {

    Customer getCustomer(Long customerId);

    Page<Customer> getAllCustomers(String customerName, String status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);

    Customer createOrUpdate(Customer customer);

    void delete(Long customerId);

    void deleteInBulk(List<Long> customerIds);
}
