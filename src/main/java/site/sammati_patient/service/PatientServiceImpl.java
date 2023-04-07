package site.sammati_patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.dto.PatientOtpDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.repository.PatientRepository;
import site.sammati_patient.util.Role;

import java.sql.Date;
import java.time.LocalDate;

import static site.sammati_patient.service.OtpAndMailService.getOPTByKey;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    @Override
    public Boolean isPatientExist(Integer patientId)
    {
        Integer id =patientRepository.patientExist(patientId);
        if(id!=null)return true;

        return false;
    }

    @Override
    public Patient findByUserName(String userName) {
        return patientRepository.findByUserName(userName).get();
    }

    @Override
    public PatientDto getPatientData(PatientOtpDto patientOtpDto)
    {
        String pto = getOPTByKey(Integer.toString(patientOtpDto.getPatientId()));
        //time expire
        if(pto==null)
        {
            return null;
        }
        System.out.println("PTO: "+pto);
        System.out.println("OTP: "+patientOtpDto.getOtp());
        //if patient type wrong otp
        if(!patientOtpDto.getOtp().equals(pto))
        {
            return null;
        }
        Patient patient=patientRepository.getPatientData(patientOtpDto.getPatientId());
        PatientDto patientDto=PatientDto.builder()
                        .patientId(patient.getPatientId())
                        .firstName(patient.getFirstName())
                        .LastName(patient.getLastName())
                        .phoneNumber(patient.getPhoneNumber())
                        .gender(patient.getGender())
                        .uidNumber(patient.getUID_Number())
                        .uidType(patient.getUID_type())
                        .email(patient.getEmail())
                        .password(patient.getPassword())
                        .DOB(patient.getDOB())
                        .registrationDate(patient.getRegistrationDate())
                        .state(patient.getState())
                        .userName(patient.getUsername())
                        .address(patient.getAddress())
                        .pinCode(patient.getPinCode())
                        .passPhoto(patient.getPassPhoto())
                        .role(patient.getRole())
                        .build();
        System.out.println(patientDto);
        return patientDto;
    }

    @Override
    public String getPatientMobileNumber(Integer patientId) {
        return patientRepository.getPatientMobile(patientId);
    }
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
