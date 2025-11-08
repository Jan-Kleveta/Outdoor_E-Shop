package cz.cvut.fel.sit.entity;

import cz.cvut.fel.sit.enums.Role;
import cz.cvut.fel.sit.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "APP_USER")
public class User extends IdEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}
