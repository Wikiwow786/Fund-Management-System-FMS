package com.fms.fund_management_system.mapper;

import com.fms.fund_management_system.entities.Bank;
import com.fms.fund_management_system.entities.User;
import com.fms.fund_management_system.models.BankModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankMapper{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    void toEntity(BankModel model, @MappingTarget Bank entity);


}
