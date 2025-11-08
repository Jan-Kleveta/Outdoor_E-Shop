package cz.cvut.fel.sit.entity;

import cz.cvut.fel.sit.enums.Prefix;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PhoneNumber extends IdEntity{

    @Enumerated(EnumType.STRING)
    @Column//(nullable = false)
    private Prefix prefix;

    @Column(nullable = false)
    private String localNumber;

    public String getFullNumber() {
        return prefix.getCode() + localNumber;
    }
}
