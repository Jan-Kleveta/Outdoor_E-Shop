package cz.cvut.fel.sit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class StripeSessionRequest {

    @NotEmpty
    @Schema(description = "Cart Items")
    private List<ProductInstanceRequest> items;
}
