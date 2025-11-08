package cz.cvut.fel.sit.dto.response;

import cz.cvut.fel.sit.enums.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OrderAddress response", name = "OrderAddressResponse")
public class OrderAddressResponse {

    @Schema(description = "Country of the address", example = "CZ")
    private Country country;

    @Schema(description = "City of the address", example = "Prague")
    private String city;

    @Schema(description = "First address line", example = "Vaclavske namesti 1")
    private String addressLine1;

    @Schema(description = "Second address line", example = "flat 2, 3rd floor")
    private String addressLine2;

    @Schema(description = "Postal code of the address", example = "11000")
    private String zipCode;

    @Schema(description = "Name of the recipient", example = "Jan Novak")
    private String name;
}
