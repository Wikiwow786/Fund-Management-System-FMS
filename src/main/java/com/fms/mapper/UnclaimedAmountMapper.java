package com.fms.mapper;


import com.fms.entities.Customer;
import com.fms.entities.UnclaimedAmount;
import com.fms.models.UnclaimedAmountModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnclaimedAmountMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "claimedBy", source = "claimedBy")
    void toEntity(UnclaimedAmountModel model, @MappingTarget UnclaimedAmount entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "claimedBy", source = "claimedBy.customerName")
    UnclaimedAmountModel toModel(UnclaimedAmount entity);


    default Customer mapStringToCustomer(String name) {
        if (name == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setCustomerName(name);
        return customer;
    }

    default String mapUserToString(Customer customer) {
        return customer != null ? customer.getCustomerName() : null;
    }
}
