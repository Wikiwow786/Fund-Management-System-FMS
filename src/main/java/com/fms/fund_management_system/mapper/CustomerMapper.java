package com.fms.fund_management_system.mapper;

import com.fms.fund_management_system.entities.Customer;
import com.fms.fund_management_system.models.CustomerModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface CustomerMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(CustomerModel model, @MappingTarget Customer entity);

    // Optional: If you need a method to map Customer -> CustomerModel
    CustomerModel toModel(Customer entity);
}
