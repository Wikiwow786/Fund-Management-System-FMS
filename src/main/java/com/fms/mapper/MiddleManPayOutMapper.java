package com.fms.mapper;

import com.fms.entities.MiddleManPayOut;
import com.fms.models.MiddleManPayOutModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MiddleManPayOutMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    void toEntity(MiddleManPayOutModel model, @MappingTarget MiddleManPayOut entity);
}
