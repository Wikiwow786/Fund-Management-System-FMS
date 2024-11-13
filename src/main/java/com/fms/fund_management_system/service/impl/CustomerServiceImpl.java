package com.fms.fund_management_system.service.impl;

import com.fms.fund_management_system.entities.Customer;
import com.fms.fund_management_system.entities.QCustomer;
import com.fms.fund_management_system.exception.ResourceNotFoundException;
import com.fms.fund_management_system.repositories.CustomerRepository;
import com.fms.fund_management_system.service.CustomerService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Override
    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

    @Override
    public Page<Customer> getAllCustomers(String customerName, String status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(search)){
            filter.and(QCustomer.customer.customerName.containsIgnoreCase(search))
                    .or(QCustomer.customer.customerName.containsIgnoreCase(search));
        }
        if (startDate != null) {
            filter.or(QCustomer.customer.startDate.goe(startDate));
        }

        if (endDate != null) {
            filter.or(QCustomer.customer.endDate.loe(endDate));
        }
        return customerRepository.findAll(filter, pageable);
    }


    @Override
    public Customer createOrUpdate(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Long customerId) {

        customerRepository.delete(customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public void deleteInBulk(List<Long> customerIds) {

        List<Customer> list = customerRepository.findAllByCustomerIdIn(customerIds);

        for (Customer customer : list) {
            customerRepository.delete(customer);
        }
    }
}
