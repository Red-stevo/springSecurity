package red.stevo.code.springsecurity.SchoolRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import red.stevo.code.springsecurity.SchoolDTO.StudentDTO;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentDTO, Long> {

    Optional<StudentDTO> findByUserName(String userName);
}
