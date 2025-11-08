package cz.cvut.fel.sit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Category response DTO")
public class CategoryResponse {

    @Schema(description = "List of subcategories", example = "[\"Pants\", \"Sweatshirts\"]")
    private List<CategoryResponse> subcategories;

    @Schema(description = "Parent category", example = "Clothing")
    private CategoryResponse parentCategory;

    @Schema(description = "Category name", example = "Clothing")
    private String name;

    @Schema(description = "Category description", example = "Clothing category")
    private String description;

    @Schema(description = "Set of products in the category", example = "[\"Product1\", \"Product2\"]")
    private Set<ProductResponse> products;
}
