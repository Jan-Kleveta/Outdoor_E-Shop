package cz.cvut.fel.sit.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "ProductInstanceRequest", name = "ProductInstanceRequest")
public class ProductInstanceRequest {

    @NotNull(message = "ProductWithSizeId must be filled")
    @Schema(description = "ProductWithSizeId", example = "1")
    private Long productWithSizeId;

    @NotNull(message = "OrderedQuantity must be filled")
    @Schema(description = "OrderedQuantity", example = "1")
    private int orderedQuantity;
}
