package cz.cvut.fel.sit.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@DiscriminatorValue("BILLING")
public class BillingAddress extends OrderAddress{


}
