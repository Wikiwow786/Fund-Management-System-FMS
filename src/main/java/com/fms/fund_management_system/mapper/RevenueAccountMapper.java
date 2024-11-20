package com.fms.fund_management_system.mapper;

import com.fms.fund_management_system.entities.RevenueAccount;
import com.fms.fund_management_system.models.RevenueAccountModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface RevenueAccountMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(RevenueAccountModel model, @MappingTarget RevenueAccount entity);
}
