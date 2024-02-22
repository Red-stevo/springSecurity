package red.stevo.code.springsecurity.StudentRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import red.stevo.code.springsecurity.StudentDAO.StudentInfoTable;

import java.util.Optional;

@Repository
public interface StudentDAORepository extends CrudRepository<StudentInfoTable, Long> {

    Optional<UserDetails> findByUserName(String userName);
}
