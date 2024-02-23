package red.stevo.code.springsecurity.AuthenticationController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.stevo.code.springsecurity.SchoolDTO.AuthenticationResponse;
import red.stevo.code.springsecurity.SchoolDTO.LoginModel;
import red.stevo.code.springsecurity.SchoolDTO.StudentDTO;
import red.stevo.code.springsecurity.StudentService.UserRegistrationService;

@RestController
@RequestMapping("/api/v0/")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody StudentDTO studentDTO)
    {
        return new ResponseEntity<>(userRegistrationService.registerUser(studentDTO),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(
            @RequestBody LoginModel loginModel
            ){
        return new ResponseEntity<>(userRegistrationService.userLogin(loginModel), HttpStatus.OK);
    }

}
