package com.fms.mapper;

import com.fms.entities.RolePermission;
import com.fms.models.RolePermissionModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolePermissionMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(RolePermissionModel model, @MappingTarget RolePermission entity);
}
