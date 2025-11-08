package cz.cvut.fel.sit.dto.request;

import cz.cvut.fel.sit.enums.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product with size request DTO")
public class ProductWithSizeRequest {

    @NotNull
    @Schema(description = "Product size", example = "L")
    private Size size;

    @NotNull
    @Schema(description = "Product stock quantity", example = "10")
    private int stockQuantity;

    @Schema(description = "Product weight", example = "1.5")
    private double weight;
}
