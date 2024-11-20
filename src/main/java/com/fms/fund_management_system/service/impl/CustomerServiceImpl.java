
package com.fms.fund_management_system.service.impl;

import com.fms.fund_management_system.entities.Customer;
import com.fms.fund_management_system.entities.QCustomer;
import com.fms.fund_management_system.entities.User;
import com.fms.fund_management_system.exception.ResourceNotFoundException;
import com.fms.fund_management_system.mapper.CustomerMapper;
import com.fms.fund_management_system.models.AuthModel;
import com.fms.fund_management_system.models.CustomerModel;
import com.fms.fund_management_system.repositories.CustomerRepository;
import com.fms.fund_management_system.repositories.UserRepository;
import com.fms.fund_management_system.service.CustomerService;
import com.fms.fund_management_system.util.BeanUtil;
import com.querydsl.core.BooleanBuilder;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
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
    private final CustomerMapper customerMapper;
    private final UserRepository userRepository;
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
        return customerRepository.findAll(filter, pageable);
    }


    @Override
    public CustomerModel createOrUpdate(CustomerModel customerModel,Long customerId,AuthModel authModel) {
        return new CustomerModel(customerRepository.save(assemble(customerModel,customerId,authModel)));

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

    public Customer assemble(CustomerModel customerModel,Long customerId,AuthModel authModel){

        Customer customer;

        User user = BeanUtil.getBean(UserRepository.class).findById(authModel.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != customerId) {
            customer = BeanUtil.getBean(CustomerRepository.class).findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            customer.setUpdatedBy(user);
        }
        else {
            customer = new Customer();
            customer.setCreatedBy(user);
        }
        customerMapper.toEntity(customerModel, customer);
        if(null != customerModel.getMiddleManId()){
            customer.setUser(userRepository.findById(customerModel.getMiddleManId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        }
        return customer;
    }
}

