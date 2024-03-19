package com.redstevo.code.sprinsecurity.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestModel {

    private String username;

    private String password;

    @JsonIgnore
    private GrantedAuthority role;

    @PostConstruct
    private void setUpDefaultAuthority(){
        this.role = new SimpleGrantedAuthority("user");
    }
}
