package red.stevo.code.springsecurity;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.stevo.code.springsecurity.AppModels.AuthenticationModel;
import red.stevo.code.springsecurity.AppModels.LoginModel;
import red.stevo.code.springsecurity.AppService.UserLogin;

@RestController
@Slf4j
@RequestMapping("/api/v0")
public class AppController {

    private final UserLogin userLogin;

    @Autowired
    public AppController(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationModel> authenticationModel(
            @RequestBody LoginModel loginModel
            )
    {
        log.info("Received request to register a new user");
        return new ResponseEntity<>(userLogin.registerUser(loginModel), HttpStatus.CREATED);
    }
}
