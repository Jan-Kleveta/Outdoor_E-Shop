package cz.cvut.fel.sit.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Phone number response", name = "PhoneNumberResponse")
public class PhoneNumberResponse {

    //FIXME
    //private Prefix prefix;

    @Schema(description = "Phone number")
    private String localNumber;
}
