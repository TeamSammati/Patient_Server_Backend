package site.sammati_patient.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.sammati_patient.entity.Patient;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

//    Optional<Patient> findOneByUserNameAndPassword(String email, String password);

//    Patient findByUserName(String email);

    Optional<Patient> findByUserName(String username);
}
