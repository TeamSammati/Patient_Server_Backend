package site.sammati_patient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import site.sammati_patient.dto.ConsentDataDTO;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ConsentDataController {

    private final Environment env;
    @PostMapping("/send-consent-data")
    public Integer sendConsentData(@RequestBody ConsentDataDTO consentDataDTO){
        String uri = "http://"+env.getProperty("app.sammati_server")+":"+env.getProperty("app.sammati_port")+"/receive-consent-data";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ConsentDataDTO> request = new HttpEntity<ConsentDataDTO>(consentDataDTO, headers);

        ResponseEntity<Integer> response = restTemplate.postForEntity( uri, request , Integer.class);
        return response.getBody();
    }

    @GetMapping("/validate-keys")
    public Integer validateKeys(@RequestParam Integer patientId, @RequestParam String otp) {
        String uri = "http://"+env.getProperty("app.sammati_server")+":"+env.getProperty("app.sammati_port")+"/validate-keys?patientId="+patientId+"&otp="+otp;
        RestTemplate restTemplate = new RestTemplate();
        Integer result = restTemplate.getForObject(uri, Integer.class);
        return result;
    }
}
