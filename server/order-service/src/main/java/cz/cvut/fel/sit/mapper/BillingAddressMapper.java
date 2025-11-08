package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.BillingAddress;
import cz.cvut.fel.sit.dto.response.BillingAddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillingAddressMapper {

    BillingAddressResponse toResponse (BillingAddress billingAddress);

}
