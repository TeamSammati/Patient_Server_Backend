package site.sammati_patient.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import site.sammati_patient.dto.ConsentDataDTO;

@RestController
@CrossOrigin("*")
public class ConsentDataController {
    @PostMapping("/send-consent-data")
    public Integer sendConsentData(@RequestBody ConsentDataDTO consentDataDTO){
        String uri = "http://172.16.133.184:6979/receive-consent-data";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ConsentDataDTO> request = new HttpEntity<ConsentDataDTO>(consentDataDTO, headers);

        ResponseEntity<Integer> response = restTemplate.postForEntity( uri, request , Integer.class);
        return response.getBody();
    }
}
