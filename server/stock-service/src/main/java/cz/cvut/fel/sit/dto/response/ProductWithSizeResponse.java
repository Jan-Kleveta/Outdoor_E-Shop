package cz.cvut.fel.sit.dto.response;

import cz.cvut.fel.sit.enums.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ProductWithSize response DTO")
public class ProductWithSizeResponse {

    @Schema(description = "Unique identifier of the product with size", example = "1")
    private Long id;

    @Schema(description = "Size of the product", example = "L")
    private Size size;

    @Schema(description = "Stock quantity of the product size", example = "100")
    private int stockQuantity;

    @Schema(description = "Weight of the product size", example = "1.5")
    private double weight;
}
