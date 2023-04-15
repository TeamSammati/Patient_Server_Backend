package site.sammati_patient.repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.entity.Patient;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

//    Optional<Patient> findOneByUserNameAndPassword(String email, String password);

//    Patient findByUserName(String email);

    Optional<Patient> findByUserName(String username);

    @Query("select p.patientId from Patient p where p.patientId=?1")
    Integer patientExist(Integer patientId);

    @Query("select p from Patient p where p.patientId=?1")
    Patient getPatientData(Integer patientId);

    @Query("select p.phoneNumber from Patient p where p.patientId=?1")
    String getPatientMobile(Integer patientId);

    @Modifying
    @Transactional
    @Query("update Patient set qrCode=?1 where patientId=?2")
    Integer addQR(String databytes, Integer patientId);

    Patient findByPatientId(Integer patientId);
}
