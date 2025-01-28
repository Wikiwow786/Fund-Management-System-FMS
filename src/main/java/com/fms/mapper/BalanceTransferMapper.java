package com.fms.mapper;

import com.fms.entities.BalanceTransfer;
import com.fms.models.BalanceTransferModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceTransferMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    void toEntity(BalanceTransferModel model, @MappingTarget BalanceTransfer entity);
}
