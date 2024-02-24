package red.stevo.code.springsecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import red.stevo.code.springsecurity.AppRepository.StudentRepository;

import java.util.concurrent.atomic.AtomicReference;

public class AppService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public AppService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
       userDetails = studentRepository.findAllByUsername(username).orElseThrow(
               () -> new UsernameNotFoundException("Username Not Found")
       );

       return userDetails;
    }
}
