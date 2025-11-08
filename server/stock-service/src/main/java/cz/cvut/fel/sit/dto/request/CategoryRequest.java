package cz.cvut.fel.sit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Category request DTO")
public class CategoryRequest {

    //@Schema(description = "List of subcategories", example = "[\"Pants\", \"Sweatshirts\"]")
    //private List<Category> subcategories;

    @Valid
    @Schema(description = "Parent category", example = "Clothing")
    private CategoryRequest parentCategory;

    @NotEmpty(message = "Category name must be filled")
    @Schema(description = "Category name", example = "Clothing")
    private String name;

    @NotEmpty(message = "Category description must be filled")
    @Schema(description = "Category description", example = "Clothing category")
    private String description;

    @Valid
    @Schema(description = "Set of products in the category", example = "[\"Product1\", \"Product2\"]")
    private Set<ProductRequest> products;
}