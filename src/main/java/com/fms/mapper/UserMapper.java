package com.fms.mapper;

import com.fms.entities.User;
import com.fms.models.UserModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(UserModel model, @MappingTarget User entity);
}
