package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.ProductWithSize;
import cz.cvut.fel.sit.dto.request.ProductWithSizeRequest;
import cz.cvut.fel.sit.dto.response.ProductWithSizeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductWithSizeMapper {

    ProductWithSize toEntity(ProductWithSizeRequest productWithSizeRequest);

    ProductWithSizeResponse toResponse(ProductWithSize productWithSize);

    default List<ProductWithSizeResponse> toResponseList(List<ProductWithSize> productsWithSize) {
        return productsWithSize.stream()
                .map(this::toResponse)
                .toList();
    }
}
