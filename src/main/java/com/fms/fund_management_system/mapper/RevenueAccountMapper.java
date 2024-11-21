package com.fms.fund_management_system.mapper;

import com.fms.fund_management_system.entities.RevenueAccount;
import com.fms.fund_management_system.models.RevenueAccountModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RevenueAccountMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(RevenueAccountModel model, @MappingTarget RevenueAccount entity);
}
