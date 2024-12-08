package com.fms.mapper;

import com.fms.entities.Bank;
import com.fms.models.BankModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankMapper{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    void toEntity(BankModel model, @MappingTarget Bank entity);


}
