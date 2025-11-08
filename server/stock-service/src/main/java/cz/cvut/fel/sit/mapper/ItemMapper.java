package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.domain.ReservedItem;
import cz.cvut.fel.sit.dto.request.ProductInstanceRequest;
import cz.cvut.fel.sit.dto.request.StripeSessionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "productWithSizeId", target = "sizeId")
    @Mapping(source = "orderedQuantity", target = "quantity")
    @Mapping(source = "currency", target = "currency")
    ReservedItem toReservedItem(ProductInstanceRequest request);

    List<ReservedItem> toReservedItemList(List<ProductInstanceRequest> requests);

    @Named("fromStripeRequest")
    default List<ReservedItem> fromStripeRequest(StripeSessionRequest stripeRequest) {
        return toReservedItemList(stripeRequest.getItems());
    }
}
