package com.fms.mapper;

import com.fms.entities.RevenueAccount;
import com.fms.models.RevenueAccountModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RevenueAccountMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(RevenueAccountModel model, @MappingTarget RevenueAccount entity);
}
