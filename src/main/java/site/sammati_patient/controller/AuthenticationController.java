package site.sammati_patient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.service.AuthenticationService;
import site.sammati_patient.service.PatientService;
import site.sammati_patient.util.AuthenticationResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody PatientDto request
    ){
        return  ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody PatientLoginDto request
    ){

        Patient patient=patientService.findByUserName(request.getUserName());
        AuthenticationResponse authenticationResponse=AuthenticationResponse.builder()
                .patient(patient)
                .token(authenticationService.authenticate(request).getToken())
                .build();
        return ResponseEntity.ok(authenticationResponse);
    }

    //As of now this method is whitelist to check the functionality(remove from whitelist)
    @PostMapping("/global_patient_id_exist/{pId}")
    public Boolean checkGlobalPatientId(@PathVariable("pId") Integer patientId)
    {
        return patientService.isPatientExist(patientId);
    }
}
