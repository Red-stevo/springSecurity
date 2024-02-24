package red.stevo.code.springsecurity.ServiceLayer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import red.stevo.code.springsecurity.AppRepository.StudentRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
@Data
@Slf4j
public class AppService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Autowired
    public AppService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("Getting the user by username");
        UserDetails userDetails;
       userDetails = studentRepository.findAllByUsername(username).orElseThrow(
               () -> {
                   log.error("Username Not Found");
                   return new UsernameNotFoundException("Username Not Found");
               }
       );

       return userDetails;
    }
}
