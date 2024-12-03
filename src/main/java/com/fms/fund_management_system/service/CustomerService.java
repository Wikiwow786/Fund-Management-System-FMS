
package com.fms.fund_management_system.service;

import com.fms.fund_management_system.entities.Customer;
import com.fms.fund_management_system.models.CustomerModel;
import com.fms.fund_management_system.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface CustomerService {

    CustomerModel getCustomer(Long customerId);

    Page<CustomerModel> getAllCustomers(String customerName, Customer.CustomerStatus status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);

    CustomerModel createOrUpdate(CustomerModel customerModel, Long customerId, SecurityUser user);

    void delete(Long customerId);

    void deleteInBulk(List<Long> customerIds);
}

