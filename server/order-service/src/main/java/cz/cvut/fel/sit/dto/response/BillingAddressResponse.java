package cz.cvut.fel.sit.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Billing address response", name = "BillingAddressResponse")
public class BillingAddressResponse extends OrderAddressResponse{
}
