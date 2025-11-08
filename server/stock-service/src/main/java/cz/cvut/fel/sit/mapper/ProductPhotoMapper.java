package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.ProductPhoto;
import cz.cvut.fel.sit.dto.request.ProductPhotoRequest;
import cz.cvut.fel.sit.dto.response.ProductPhotoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPhotoMapper {
    ProductPhoto toEntity(ProductPhotoRequest photoRequest);

    ProductPhotoResponse toResponse(ProductPhoto productPhoto);

    default List<ProductPhotoResponse> toResponseList(List<ProductPhoto> photos) {
        return photos.stream()
                .map(this::toResponse)
                .toList();
    }
}
