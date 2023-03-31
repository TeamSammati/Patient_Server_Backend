package site.sammati_patient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.service.AuthenticationService;
import site.sammati_patient.service.PatientService;
import site.sammati_patient.util.AuthenticationResponse;

import java.util.List;

import static site.sammati_patient.service.OtpService.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")
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

    @PostMapping("/gs_otp")
    public Integer gsOTP(@RequestParam("phoneNumber") String phno) {
        Integer otp = generateOTP(phno);
        System.out.println(otp);
        Integer re = sendOTP(phno, otp.toString());
        return re;
    }

    @PostMapping("/validate_otp")
    public boolean validateOTP(@RequestParam("phoneNumber") String phno, @RequestParam("otp") String otp) {
        String pto = getOPTByKey(phno);
        if(pto==null)
            return false;
        System.out.println("PTO: "+pto);
        System.out.println("OTP: "+otp);

        return otp.equals(pto);
    }
}
