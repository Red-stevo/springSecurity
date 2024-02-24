package red.stevo.code.springsecurity.AppService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import red.stevo.code.springsecurity.AppDTO.StudentDTO;
import red.stevo.code.springsecurity.AppModels.AuthenticationModel;
import red.stevo.code.springsecurity.AppModels.LoginModel;
import red.stevo.code.springsecurity.AppRepository.StudentRepository;
import red.stevo.code.springsecurity.ServiceLayer.JwtService;

import java.util.Date;

@Service
@Slf4j
public class UserLogin {

    private final StudentRepository studentRepository;

    private final JwtService jwtService;
    @Autowired
    public UserLogin(StudentRepository studentRepository, JwtService jwtService) {
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
    }

    public AuthenticationModel registerUser(LoginModel loginModel) {
        StudentDTO studentDTO = new StudentDTO();

        log.info("Saving the student to the database.");
        studentDTO.setUsername(loginModel.getUsername());
        studentDTO.setPassword(loginModel.getPassword());
        studentDTO.setRole(loginModel.getRole());

        studentRepository.save(studentDTO);

        log.info("generating the jwt token");

        String jwtToken = jwtService.generateJwtToken(studentDTO.getUsername());

        /*Preparing the response*/
        log.info("Processing the response");
        AuthenticationModel authenticationModel = new AuthenticationModel();
        authenticationModel.setDate(new Date());
        authenticationModel.setMessage("User created successfully");
        authenticationModel.setJwtToken(jwtToken);
        authenticationModel.setHttpStatus(HttpStatus.CREATED);

        return authenticationModel;
    }
}
