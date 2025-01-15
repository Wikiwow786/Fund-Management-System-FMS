package com.fms.mapper;

import com.fms.entities.ManualEntry;
import com.fms.models.ManualEntryModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManualEntryMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    void toEntity(ManualEntryModel model, @MappingTarget ManualEntry entity);
}
