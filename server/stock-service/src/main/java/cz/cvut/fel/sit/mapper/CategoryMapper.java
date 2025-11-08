package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.Category;
import cz.cvut.fel.sit.dto.request.CategoryRequest;
import cz.cvut.fel.sit.dto.response.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest categoryRequest);


    CategoryResponse toResponse(Category category);
}
