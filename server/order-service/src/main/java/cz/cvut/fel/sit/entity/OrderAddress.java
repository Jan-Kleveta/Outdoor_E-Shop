package cz.cvut.fel.sit.entity;

import cz.cvut.fel.sit.enums.Country;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "address_type")
public abstract class OrderAddress extends IdEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Country country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String addressLine1;

    //TODO: Ok?
    @Column//(nullable = false)
    private String addressLine2;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String name;

}
