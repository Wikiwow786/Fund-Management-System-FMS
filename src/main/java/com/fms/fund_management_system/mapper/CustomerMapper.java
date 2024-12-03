package com.fms.fund_management_system.mapper;

import com.fms.fund_management_system.entities.Customer;
import com.fms.fund_management_system.models.CustomerModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(CustomerModel model, @MappingTarget Customer entity);
}
