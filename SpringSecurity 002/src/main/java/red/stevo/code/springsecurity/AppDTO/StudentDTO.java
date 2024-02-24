package red.stevo.code.springsecurity.AppDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import red.stevo.code.springsecurity.AppModels.Role;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "student_registration")
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private Role role;


    public StudentDTO(StudentDTO studentDTO)
    {
        this.id = studentDTO.getId();
        this.username = studentDTO.getUsername();
        this.password = studentDTO.getPassword();
        this.role = studentDTO.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
