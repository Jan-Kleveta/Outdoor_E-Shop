package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.ProductInstance;
import cz.cvut.fel.sit.dto.request.ProductInstanceRequest;
import cz.cvut.fel.sit.dto.response.ProductInstanceResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductInstanceMapper {

    ProductInstanceResponse toResponse (ProductInstance productInstance);
    ProductInstance toEntity(ProductInstanceRequest productInstanceRequest);

    default List<ProductInstanceResponse> toResponseList(List<ProductInstance> productInstances) {
        return productInstances.stream()
                .map(this::toResponse)
                .toList();
    }
}
