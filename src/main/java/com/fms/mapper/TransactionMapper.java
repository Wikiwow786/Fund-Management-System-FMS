package com.fms.mapper;

import com.fms.entities.Transaction;
import com.fms.models.TransactionModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    void toEntity(TransactionModel model, @MappingTarget Transaction entity);
}
