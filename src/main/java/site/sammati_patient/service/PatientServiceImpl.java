package site.sammati_patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.sammati_patient.Responce.LoginRes;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.repository.PatientRepository;
import site.sammati_patient.conf.Configure;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String addPatient(PatientDto patientDto) {
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
                patientDto.getAddress(),
                patientDto.getPinCode(),
                patientDto.getPassPhoto()
        );
        employee.setRegistrationDate(date);
        repo.save(employee);

        return employee.getFirstName();
    }
    PatientDto patientDto;

    @Override
    public LoginRes loginPatient(PatientLoginDto loginDTO) {
        String msg = "";
        Patient employee1 = repo.findByPhoneNumber(loginDTO.getPhoneNumber());
        if (employee1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = employee1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password,encodedPassword);
            if (isPwdRight) {
                Optional<Patient> employee = repo.findOneByPhoneNumberAndPassword(loginDTO.getPhoneNumber(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginRes("Login Success", true);
                } else {
                    return new LoginRes("Login Failed", false);
                }
            } else {

                return new LoginRes("password Not Match", false);
            }
        }else {
            return new LoginRes("Email not exits", false);
        }
    }
}
