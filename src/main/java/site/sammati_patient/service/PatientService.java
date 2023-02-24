package site.sammati_patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import site.sammati_patient.Responce.LoginRes;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;

@Component
public interface PatientService {

    public String addPatient(PatientDto patientDto);

    public LoginRes loginPatient(PatientLoginDto patientDto);
}
