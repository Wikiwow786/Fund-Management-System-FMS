package com.fms.mapper;

import com.fms.entities.Customer;
import com.fms.models.CustomerModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(CustomerModel model, @MappingTarget Customer entity);
}
