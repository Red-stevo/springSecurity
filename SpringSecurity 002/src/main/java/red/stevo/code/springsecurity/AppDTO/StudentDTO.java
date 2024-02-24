package red.stevo.code.springsecurity.AppDTO;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import red.stevo.code.springsecurity.AppModels.Role;

import java.util.Collection;
import java.util.List;

public class StudentDTO implements UserDetails {

    String username;

    String password;

    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        role = new Role();
        return List.of(new SimpleGrantedAuthority(role.getROLE1()), new SimpleGrantedAuthority(role.getROLE1()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
        return true;
    }
}
