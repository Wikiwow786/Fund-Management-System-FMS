
package com.fms.service;

import com.fms.entities.Customer;
import com.fms.models.CustomerModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface CustomerService {

    CustomerModel getCustomer(Long customerId);

    Page<CustomerModel> getAllCustomers(String customerName, Customer.CustomerStatus status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);

    CustomerModel createOrUpdate(CustomerModel customerModel, Long customerId, SecurityUser user);

}

