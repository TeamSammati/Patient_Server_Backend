package site.sammati_patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
//import site.sammati_patient.Responce.LoginRes;
import site.sammati_patient.dto.PatientDetailsDto;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.dto.PatientOtpDto;
import site.sammati_patient.entity.Patient;

@Component
public interface PatientService {
    Boolean isPatientExist(Integer patientId);

    Patient findByUserName(String userName);
    PatientDto getPatientData(PatientOtpDto patientOtpDto);

    String  getPatientMobileNumber(Integer patientId);

    PatientDetailsDto getPatientDetails(Integer patientId);
//
//    public Integer addPatient(PatientDto patientDto);
//
//    public Patient loginPatient(PatientLoginDto patientDto);
}
