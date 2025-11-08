package cz.cvut.fel.sit.dto.response;

import cz.cvut.fel.sit.enums.VAT;
import cz.cvut.fel.sit.enums.VisibilityStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product response DTO")
public class ProductResponse {

    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @Schema(description = "Name of the product", example = "Product Name")
    private String name;

    @Schema(description = "Description of the product", example = "Best product in the world")
    private String description;

    @Schema(description = "Visibility status of the product size", example = "VISIBLE")
    private VisibilityStatus visibilityStatus;

    @Schema(description = "VAT of the product size", example = "HIGH")
    private VAT vat;

    @Schema(description = "List of prices for the product")
    private List<PriceResponse> prices = new ArrayList<>();

    @Schema(description = "List of product photos")
    private List<ProductPhotoResponse> productPhotos = new ArrayList<>();

    @Schema(description = "List of products with sizes")
    private List<ProductWithSizeResponse> productsWithSize = new ArrayList<>();
}
