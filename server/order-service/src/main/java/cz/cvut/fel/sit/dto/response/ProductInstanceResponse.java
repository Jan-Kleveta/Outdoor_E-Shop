package cz.cvut.fel.sit.dto.response;

import cz.cvut.fel.sit.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "ProductInstanceRequest", name = "ProductInstanceRequest")
public class ProductInstanceResponse {

    @Schema(description = "Name of the product with size.", example = "Crewneck M")
    private String productName;

    @Schema(description = "Ordered quantity of specific product and size.", example = "1")
    private int orderedQuantity;

    @Schema(description = "Price per unit of ordered product.", example = "100.00")
    private BigDecimal pricePerUnit;

    //FIXME: currency probably redundant? part of order
    @Schema(description = "Currency of the price.", example = "eur")
    private Currency currency;
}
