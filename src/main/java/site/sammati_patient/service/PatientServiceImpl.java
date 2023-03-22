package site.sammati_patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.repository.PatientRepository;
import site.sammati_patient.util.Role;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class PatientServiceImpl implements PatientService {
//    @Autowired
//    private PatientRepository repo;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @Override
//    public Integer addPatient(PatientDto patientDto) {
//        Date date = Date.valueOf(LocalDate.now());
//        Patient employee = Patient.builder()
//                .firstName(patientDto.getFirstName())
//                .lastName(patientDto.getLastName())
//                .phoneNumber(patientDto.getPhoneNumber())
//                .gender(patientDto.getGender())
//                .email(patientDto.getEmail())
//                .DOB(patientDto.getDOB())
//                .address(patientDto.getAddress())
//                .pinCode(patientDto.getPinCode())
//                .role(Role.USER)
//                .state(patientDto.getState())
//                .registrationDate(date)
//                .UID_Number(patientDto.getUID_Number())
//                .UID_type(patientDto.getUID_type())
//                .passPhoto(patientDto.getPassPhoto())
//                .userName(patientDto.getUserName())
//                .password(this.passwordEncoder.encode(patientDto.getPassword()))
//                .build();
//        employee.setRegistrationDate(date);
//        repo.save(employee);
//
//        return employee.getPatientId();
//    }
//    PatientDto patientDto;
//
//    @Override
//    public Patient loginPatient(PatientLoginDto loginDTO) {
//        String msg = "";
//        Patient patient = repo.findByUserName(loginDTO.getUserName()).get();
//        if (patient != null) {
//            String password = loginDTO.getPassword();
//            String encodedPassword = patient.getPassword();
//            Boolean isPwdRight = passwordEncoder.matches(password,encodedPassword);
//            if (isPwdRight) {
//                return patient;
//            } else {
//                return null;
//            }
//        }else {
//            return null;
//        }
//    }


}
