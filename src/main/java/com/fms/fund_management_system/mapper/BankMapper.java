package com.fms.fund_management_system.mapper;

import com.fms.fund_management_system.entities.Bank;
import com.fms.fund_management_system.models.BankModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface BankMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(BankModel model, @MappingTarget Bank entity);
}
