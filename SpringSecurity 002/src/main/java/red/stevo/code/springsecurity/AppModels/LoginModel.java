package red.stevo.code.springsecurity.AppModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginModel {

    private String username;

    private  String password;

    private Role role;
}
