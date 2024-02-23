package red.stevo.code.springsecurity.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import red.stevo.code.springsecurity.Filters.JwtAuthenticationFilter;
import red.stevo.code.springsecurity.SchoolDTO.StudentDTO;
import red.stevo.code.springsecurity.SchoolRepo.StudentRepository;

@Service
public class UserRegistrationController {

    private final StudentRepository studentRepository;

    private final JwtService jwtService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationController(StudentRepository studentRepository, JwtService jwtService, JwtAuthenticationFilter jwtAuthenticationFilter, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse registerUser(StudentDTO request)
    {
        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setUserName(request.getUsername());
        studentDTO.setPassword(passwordEncoder.encode(request.getPassword()));
        studentDTO.setRole(studentDTO.getRole());

        studentRepository.save(studentDTO);

        /*generation the jwt token*/

        String  jwtToken = jwtService.generateJwtToken(studentDTO);

        return new AuthenticationResponse(jwtToken);
    }

}
