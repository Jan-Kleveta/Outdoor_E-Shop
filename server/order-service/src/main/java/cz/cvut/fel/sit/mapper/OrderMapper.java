package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.Order;
import cz.cvut.fel.sit.dto.request.OrderRequest;
import cz.cvut.fel.sit.dto.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        BillingAddressMapper.class,
        ShippingAddressMapper.class,
        ShippingMethodMapper.class,
        ProductInstanceMapper.class
})
public interface OrderMapper {

    @Mapping(source = "billingAddress", target = "billingAddressResponse")
    @Mapping(source = "shippingAddress", target = "shippingAddressResponse")
    @Mapping(source = "shippingMethod", target = "shippingMethodResponse")
    @Mapping(source = "productInstanceList", target = "productInstanceList")
    OrderResponse toResponse (Order order);
    Order toEntity(OrderRequest orderRequest);

    default List<OrderResponse> toResponseList(List<Order> orders) {
        return orders.stream()
                .map(this::toResponse)
                .toList();
    }
}



