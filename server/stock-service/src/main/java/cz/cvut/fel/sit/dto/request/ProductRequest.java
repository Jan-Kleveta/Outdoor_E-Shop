package cz.cvut.fel.sit.dto.request;

import cz.cvut.fel.sit.enums.VAT;
import cz.cvut.fel.sit.enums.VisibilityStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@Schema(description = "Product request DTO")
public class ProductRequest {

    @Schema(description = "Description of the product", example = "Best product in the world")
    private String description;

    @NotBlank(message = "Product name can't be blank")
    @Schema(description = "Name of the product", example = "Product Name")
    private String name;

    @NotNull
    @Schema(description = "Product visibility status", example = "VISIBLE")
    private VisibilityStatus visibilityStatus;

    @NotNull
    @Schema(description = "Product VAT", example = "DPH_21")
    private VAT vat;

}
