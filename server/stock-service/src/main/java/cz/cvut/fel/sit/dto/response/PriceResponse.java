package cz.cvut.fel.sit.dto.response;

import cz.cvut.fel.sit.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Create price request")
public class PriceResponse {

    @Schema(description = "Unique identifier of the price", example = "1")
    private Long id;

    @Schema(description = "Price", example = "100.00")
    private BigDecimal price;

    @Schema(description = "Currency", example = "CZK")
    private Currency currency;
}
