package red.stevo.code.springsecurity.AppRepository;

import org.springframework.data.repository.CrudRepository;
import red.stevo.code.springsecurity.AppDTO.StudentDTO;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentDTO, Long> {


    Optional<StudentDTO> findAllByUsername(String username);
}
