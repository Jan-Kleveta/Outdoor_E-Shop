package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.ShippingAddress;
import cz.cvut.fel.sit.dto.response.ShippingAddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingAddressMapper {

    ShippingAddressResponse toResponse (ShippingAddress shippingAddress);

}
