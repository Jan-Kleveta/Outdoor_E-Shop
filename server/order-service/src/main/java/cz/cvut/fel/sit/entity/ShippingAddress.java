package cz.cvut.fel.sit.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DiscriminatorValue("SHIPPING")
public class ShippingAddress extends OrderAddress {

    //FIXME
    @OneToOne(cascade = CascadeType.ALL)//, optional = false)
    @JoinColumn(name = "phone_number_id")//, nullable = false)
    private PhoneNumber phoneNumber;
}
