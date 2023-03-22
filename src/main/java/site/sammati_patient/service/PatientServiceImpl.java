package site.sammati_patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.repository.PatientRepository;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Integer addPatient(PatientDto patientDto) {
        Date date = Date.valueOf(LocalDate.now());
        Patient employee = new Patient(
                patientDto.getPatientId(),
                patientDto.getFirstName(),
                patientDto.getLastName(),
                patientDto.getPhoneNumber(),
                patientDto.getGender(),
                patientDto.getUID_Number(),
                patientDto.getUID_type(),
                patientDto.getEmail(),
                this.passwordEncoder.encode(patientDto.getPassword()),
                patientDto.getDOB(),
                patientDto.getRegistrationDate(),
                patientDto.getState(),
                patientDto.getUserName(),
                patientDto.getAddress(),
                patientDto.getPinCode(),
                patientDto.getPassPhoto()
        );
        employee.setRegistrationDate(date);
        repo.save(employee);

        return employee.getPatientId();
    }
    PatientDto patientDto;

    @Override
    public Patient loginPatient(PatientLoginDto loginDTO) {
        String msg = "";
        Patient patient = repo.findByUserName(loginDTO.getUserName());
        if (patient != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = patient.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password,encodedPassword);
            if (isPwdRight) {
                return patient;
            } else {
                return null;
            }
        }else {
            return null;
        }
    }
}
