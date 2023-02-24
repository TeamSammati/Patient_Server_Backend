package site.sammati_patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import site.sammati_patient.Responce.LoginRes;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.service.PatientService;

import java.util.List;
import java.util.Objects;

@RestController
public class PatientController {
    @GetMapping("Request_List/{id}")
    public List<Object> getConsentRequestByPid(@PathVariable("id") Integer patientId) {
        String uri = "http://172.16.133.184:6969/Request_List/" + patientId;
        //IP of Sammati server/API call
        RestTemplate restTemplate = new RestTemplate();
        List<Object> result = restTemplate.getForObject(uri, List.class);
        return result;
    }

    @PostMapping("/response/{crid}/{status}")
    public Integer generateResponse(@PathVariable("crid") Integer crid, @PathVariable("status") Integer status) {
        String uri = "http://172.16.133.184:6969/response/" + crid + "/" + status;
        //IP of Sammati server/API call
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(uri, null, Integer.class).getBody();
    }


    @Autowired
    private PatientService patientService;
    @PostMapping("/save")
    public String savePatient(@RequestBody PatientDto patientDto){
        String id=patientService.addPatient(patientDto);
        return id;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPatient(@RequestBody PatientLoginDto patientDto){
//        System.out.printf(patientDto.getPhoneNumber());
        LoginRes loginMessage=patientService.loginPatient(patientDto);
        return ResponseEntity.ok(loginMessage);
    }
}
