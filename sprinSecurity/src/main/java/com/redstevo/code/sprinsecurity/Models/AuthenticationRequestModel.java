package com.redstevo.code.sprinsecurity.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestModel {

    private String username;

    private String password;

    @JsonIgnore
    private GrantedAuthority role = new SimpleGrantedAuthority("user");

}
