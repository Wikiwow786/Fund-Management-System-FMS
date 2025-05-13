package com.fms.mapper;


import com.fms.entities.Transaction;
import com.fms.entities.User;
import com.fms.models.TransactionModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdBy", source = "createdBy")

    void toEntity(TransactionModel model, @MappingTarget Transaction entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdBy", source = "createdBy.name")
    TransactionModel toModel(Transaction entity);

    // Custom method to map String to User
    default User mapStringToUser(String name) {
        if (name == null) {
            return null;
        }
        User user = new User();
        user.setName(name);
        return user;
    }

    // Custom method to map User to String
    default String mapUserToString(User user) {
        return user != null ? user.getName() : null;
    }
}
