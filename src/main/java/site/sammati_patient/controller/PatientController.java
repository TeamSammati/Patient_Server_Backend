package site.sammati_patient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientOtpDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.service.PatientService;

import java.util.List;

import static site.sammati_patient.service.OtpAndMailService.generateOTP;
import static site.sammati_patient.service.OtpAndMailService.sendOTP;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class PatientController {

    @Autowired
    private final Environment env;

    @PostMapping("/global-patient-id-exist/{pId}")
    public Boolean checkGlobalPatientId(@PathVariable("pId") Integer patientId)
    {
        return patientService.isPatientExist(patientId);
    }

    @GetMapping("/generate-otp-patient/{patientId}")
    public Boolean sendOtpToPatient(@PathVariable Integer patientId)
    {
        String mobileNumber= patientService.getPatientMobileNumber(patientId);
        if(mobileNumber==null)
        {
            return(false);
        }
        Integer otp = generateOTP(Integer.toString(patientId));
        System.out.println(otp);
        Integer re = sendOTP(mobileNumber, otp.toString());
        return (true);
    }

    private final PatientService patientService;

    @PostMapping("/send-patient-data")
    public PatientDto getData(@RequestBody PatientOtpDto patientOtpDto)
    {
        return patientService.getPatientData(patientOtpDto);
    }

    @GetMapping("request-list")
    public ResponseEntity<Object> getConsentRequestByPid(@RequestParam("patientId") Integer patientId) {
        System.out.println("in patient");
        String uri = "http://"+env.getProperty("app.sammati_server")+":"+env.getProperty("app.sammati_port")+"/request-list/" + patientId;
        //IP of Sammati server/API call
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+env.getProperty("app.sammati_token"));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
//        List<Object> result = restTemplate.getForObject(uri, List.class);
        ResponseEntity<Object> result = restTemplate.exchange(
                uri, HttpMethod.GET, requestEntity, Object.class);
        return result;

    }

    @PostMapping("/response")
    public Integer generateResponse(@RequestParam("crid") Integer crid, @RequestParam("status") Integer status) {
        String uri = "http://"+env.getProperty("app.sammati_server")+":"+env.getProperty("app.sammati_port")+"/response/" + crid + "/" + status;
        //IP of Sammati server/API call
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+env.getProperty("app.sammati_token"));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(uri,HttpMethod.POST,requestEntity,Integer.class).getBody();
//        return restTemplate.postForEntity(uri, requestEntity, Integer.class).getBody();
    }
    @PostMapping("/get-records")
    public ResponseEntity<Object> getRecords(@RequestParam Integer patientID,@RequestParam Integer reqType)
    {
        String uri = "http://"+env.getProperty("app.sammati_server")+":"+env.getProperty("app.sammati_port")+"/handle-records?patientID="+patientID+"&"+"reqType="+reqType;
        //IP of Sammati server/API call
        System.out.println("sasasas");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+env.getProperty("app.sammati_token"));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Object.class);
        return result;
    }

    @GetMapping("/active-consents")
    public List<Object> activeConsent(@RequestParam Integer patientId){
        String uri = "http://"+env.getProperty("app.sammati_server")+":"+env.getProperty("app.sammati_port")+"/active-consents?patientID="+patientId;
        RestTemplate restTemplate = new RestTemplate();
        List<Object> result = restTemplate.getForObject(uri, List.class);
        return result;
    }

    @PostMapping("/revoke-consent")
    public Boolean revokeConsent(@RequestParam Integer consentId){
        String uri = "http://"+env.getProperty("app.sammati_server")+":"+env.getProperty("app.sammati_port")+"/revoke-consent?consentId="+consentId;
        RestTemplate restTemplate = new RestTemplate();
        Boolean result = restTemplate.getForObject(uri, Boolean.class);
        return result;
    }
}
