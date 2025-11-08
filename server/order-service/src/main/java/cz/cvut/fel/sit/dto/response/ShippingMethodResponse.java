package cz.cvut.fel.sit.dto.response;

import cz.cvut.fel.sit.enums.Carrier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Shipping method response", name = "ShippingMethodResponse")
public class ShippingMethodResponse {

    //FIXME: add currency? probably not, part of the order
    @Schema(description = "Price of the shipping", example = "100")
    private BigDecimal price;

    @Schema(description = "Tracking number provided by carrier")
    private String trackingNumber;

    @Schema(description = "Carrier used for shipping", example = "DHL")
    private Carrier carrier;
}
