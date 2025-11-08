package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.OrderAddress;
import cz.cvut.fel.sit.dto.response.OrderAddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderAddressMapper {

    OrderAddressResponse toResponse (OrderAddress orderAddress);

}
