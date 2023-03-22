package site.sammati_patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.service.PatientService;

import java.util.List;

import static site.sammati_patient.service.OtpService.*;

@RestController
public class PatientController {

    @GetMapping("Request_List")
    public List<Object> getConsentRequestByPid(@RequestParam("patientId") Integer patientId) {
        String uri = "http://172.16.133.184:6969/Request_List/" + patientId;
        //IP of Sammati server/API call
        RestTemplate restTemplate = new RestTemplate();
        List<Object> result = restTemplate.getForObject(uri, List.class);
        return result;
    }

    @PostMapping("/response")
    public Integer generateResponse(@RequestParam("crid") Integer crid, @RequestParam("status") Integer status) {
        String uri = "http://172.16.133.184:6969/response/" + crid + "/" + status;
        //IP of Sammati server/API call
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(uri, null, Integer.class).getBody();
    }


//    @Autowired
//    private PatientService patientService;
//    @PostMapping("/save")
//    public Integer savePatient(@RequestBody PatientDto patientDto){
//        int id=patientService.addPatient(patientDto);
//        return id;
//    }
//
//    @PostMapping("/login")
//    public Patient loginPatient(@RequestBody PatientLoginDto patientDto){
////        System.out.printf(patientDto.getPhoneNumber());
//        Patient patient=patientService.loginPatient(patientDto);
//        return patient;
//    }

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
