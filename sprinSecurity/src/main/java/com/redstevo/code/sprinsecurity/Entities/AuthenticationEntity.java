package com.redstevo.code.sprinsecurity.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(min = 2, max = 254, message = "Username must be between 2 to 254 character.")
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private GrantedAuthority role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public AuthenticationEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public AuthenticationEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public AuthenticationEntity setRole(GrantedAuthority role) {
        this.role = role;
        return this;
    }

    public AuthenticationEntity build(){
        return this;
    }
}
