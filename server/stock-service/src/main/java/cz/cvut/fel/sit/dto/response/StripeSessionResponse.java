package cz.cvut.fel.sit.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Stripe Session response DTO")
public class StripeSessionResponse {

    @Schema(description = "URL of stripe session", example = "URL")
    private String url;
}
