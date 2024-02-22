package red.stevo.code.springsecurity.SchoolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import red.stevo.code.springsecurity.SchoolRepo.StudentRepository;

@Service
public class StudentService implements UserDetailsService {

    private StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return studentRepository.findByUserName(username).orElseThrow(() ->
            new UsernameNotFoundException("The User Does Not Exist")
        );
    }
}
