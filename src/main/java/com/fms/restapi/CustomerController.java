
package com.fms.restapi;

import com.fms.entities.Customer;
import com.fms.models.BankModel;
import com.fms.models.CustomerModel;
import com.fms.models.CustomerResponse;
import com.fms.security.SecurityUser;
import com.fms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

@RequestMapping(value = "/customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> fetchAll(@RequestParam(required = false) String customerName,
                                                           @RequestParam(required = false) Customer.CustomerStatus status,
                                                           @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                           @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate, @RequestParam(required = false) String search,
                                                           Pageable pageable) {
        Page<CustomerModel> customerModels = customerService.getAllCustomers(customerName,status,startDate,endDate, search, pageable);
        Optional<OffsetDateTime> lastUpdatedTime = customerModels.stream()
                .map(CustomerModel::getUpdatedAt)
                .filter(Objects::nonNull)
                .max(OffsetDateTime::compareTo);
        BigDecimal totalCustomerBalance = customerModels.stream()
                .map(CustomerModel::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomerModels(customerModels);
        customerResponse.setLastUpdatedAt(lastUpdatedTime);
        customerResponse.setTotalBalance(totalCustomerBalance);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerModel> fetch(@PathVariable(value = "customerId") final Long customerId) {
        return ResponseEntity.ok((customerService.getCustomer(customerId)));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerModel> save(@RequestBody CustomerModel customerModel, @AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok((customerService.createOrUpdate(customerModel,null,user)));
    }

    @PutMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerModel> update(@PathVariable(value = "customerId") final Long customerId,
                                            @RequestBody CustomerModel customerModel,
                                                @AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok((customerService.createOrUpdate(customerModel,customerId,user)));
    }

}

