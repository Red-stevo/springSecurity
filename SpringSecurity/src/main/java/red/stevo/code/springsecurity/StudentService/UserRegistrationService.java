package red.stevo.code.springsecurity.StudentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import red.stevo.code.springsecurity.SchoolDTO.AuthenticationResponse;
import red.stevo.code.springsecurity.SchoolDTO.LoginModel;
import red.stevo.code.springsecurity.SchoolDTO.StudentDTO;
import red.stevo.code.springsecurity.SchoolRepo.StudentRepository;

@Service
@Slf4j
public class UserRegistrationService {

    private final StudentRepository studentRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    @Autowired
    public UserRegistrationService(StudentRepository studentRepository,
                                   JwtService jwtService,
                                   PasswordEncoder passwordEncoder,
                                   AuthenticationManager authenticationManager) {
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse registerUser(StudentDTO request)
    {

        log.info("Forwarded the register user request.");
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setUserName(request.getUsername());
        studentDTO.setPassword(passwordEncoder.encode(request.getPassword()));
        studentDTO.setRole(studentDTO.getRole());

        log.info("saving user to the database");
        studentRepository.save(studentDTO);

        /*generation the jwt token*/

        log.info("processing the response");
        String  jwtToken = jwtService.generateJwtToken(studentDTO);

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse userLogin(LoginModel loginModel)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginModel.getUsername(),
                        loginModel.getPassword()
                )
        );

        StudentDTO studentDTO = studentRepository.findByUserName(loginModel.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User was no found")
        );

        String jwtToken = jwtService.generateJwtToken(studentDTO);

        return new AuthenticationResponse(jwtToken);
    }

}
