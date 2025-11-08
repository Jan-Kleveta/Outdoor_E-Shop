package cz.cvut.fel.sit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Shipping address response", name = "ShippingAddressResponse")
public class ShippingAddressResponse extends OrderAddressResponse{

    @Schema(description = "Phone number", example = "+420123123123")
    private PhoneNumberResponse phoneNumber;
}
