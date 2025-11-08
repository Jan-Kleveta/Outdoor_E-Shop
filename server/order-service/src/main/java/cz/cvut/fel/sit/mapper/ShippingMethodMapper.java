package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.ShippingMethod;
import cz.cvut.fel.sit.dto.response.ShippingMethodResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingMethodMapper {

    ShippingMethodResponse toResponse (ShippingMethod shippingMethod);

}