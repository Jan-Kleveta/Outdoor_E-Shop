package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.Price;
import cz.cvut.fel.sit.dto.request.PriceRequest;
import cz.cvut.fel.sit.dto.response.PriceResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toEntity(PriceRequest priceRequest);

    PriceResponse toResponse(Price price);

    default List<PriceResponse> toResponseList(List<Price> prices) {
        return prices.stream()
                .map(this::toResponse)
                .toList();
    }
}

