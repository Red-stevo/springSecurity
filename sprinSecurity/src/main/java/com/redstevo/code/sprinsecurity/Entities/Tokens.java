package com.redstevo.code.sprinsecurity.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Tokens {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String token;

    private Boolean isLoggedOut;

    @ManyToOne
    @JoinColumn(name = "fk_authentication")
    private AuthenticationEntity authentication;
}
