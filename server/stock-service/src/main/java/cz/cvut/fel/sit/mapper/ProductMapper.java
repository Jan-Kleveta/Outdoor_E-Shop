package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.Product;
import cz.cvut.fel.sit.dto.request.ProductRequest;
import cz.cvut.fel.sit.dto.response.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequest productRequest);

    ProductResponse toResponse(Product product);

    default List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream()
                .map(this::toResponse)
                .toList();
    }
}
