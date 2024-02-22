package red.stevo.code.springsecurity.StudentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import red.stevo.code.springsecurity.StudentRepository.StudentDAORepository;

@Service
@Slf4j
public class StudentService implements UserDetailsService {

    private final StudentDAORepository studentDAORepository;

    @Autowired
    public StudentService(StudentDAORepository studentDAORepository) {
        this.studentDAORepository = studentDAORepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return studentDAORepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException("User Is Not Registered")
        );
    }
}
