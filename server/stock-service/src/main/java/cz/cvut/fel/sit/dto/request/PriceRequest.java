package cz.cvut.fel.sit.dto.request;

import cz.cvut.fel.sit.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Create price request")
public class PriceRequest {

    @NotNull
    @Schema(description = "Price", example = "100.00")
    private BigDecimal price;

    @NotNull
    @Schema(description = "Currency", example = "czk")
    private Currency currency;
}
