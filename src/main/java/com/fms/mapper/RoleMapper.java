package com.fms.mapper;

import com.fms.entities.Role;
import com.fms.models.RoleModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(RoleModel model, @MappingTarget Role entity);
}
