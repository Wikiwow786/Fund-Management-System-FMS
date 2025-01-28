
package com.fms.service.impl;

import com.fms.entities.Customer;
import com.fms.entities.QCustomer;
import com.fms.entities.User;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.CustomerMapper;
import com.fms.models.CustomerModel;
import com.fms.repositories.CustomerRepository;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
import com.fms.service.CustomerService;
import com.querydsl.core.BooleanBuilder;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserRepository userRepository;
    @Override
    public CustomerModel getCustomer(Long customerId) {
        return new CustomerModel(customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<CustomerModel> getAllCustomers(String customerName, Customer.CustomerStatus status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(search)){
            filter.and(QCustomer.customer.customerName.containsIgnoreCase(search));
        }
        if(startDate != null){
            filter.and(QCustomer.customer.createdAt.goe(startDate.atStartOfDay().atOffset(ZoneOffset.UTC)));
        }
        if(endDate != null){
            filter.and(QCustomer.customer.createdAt.loe(endDate.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC)));
        }
        return customerRepository.findAll(filter, pageable).map(CustomerModel::new);
    }


    @Override
    public CustomerModel createOrUpdate(CustomerModel customerModel, Long customerId, SecurityUser user) {
        return new CustomerModel(customerRepository.save(assemble(customerModel,customerId,user)));

    }

    public Customer assemble(CustomerModel customerModel,Long customerId,SecurityUser user){

        Customer customer;

        User loginUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != customerId) {
            customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            customer.setUpdatedBy(loginUser);
        }
        else {
            customer = new Customer();
            customer.setCreatedBy(loginUser);
        }
        customerMapper.toEntity(customerModel, customer);
        if(null != customerModel.getMiddleManId()){
            customer.setUser(userRepository.findById(customerModel.getMiddleManId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        }
        return customer;
    }
}

