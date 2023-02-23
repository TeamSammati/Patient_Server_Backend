package site.sammati_patient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class PatientController {
    @GetMapping("Request_List/{id}")
    public List<Object> getConsentRequestByPid(@PathVariable("id") Integer patientId){
        String uri = "http://172.16.133.184:6969/Request_List/"+patientId;
        //IP of Sammati server/API call
        RestTemplate restTemplate = new RestTemplate();
        List<Object> result = restTemplate.getForObject(uri, List.class);
        return result;
    }

    @PostMapping("/response/{crid}/{status}")
    public Integer generateResponse(@PathVariable("crid") Integer crid,@PathVariable("status") Integer status){
        String uri = "http://172.16.133.184:6969/response/"+crid+"/"+status;
        //IP of Sammati server/API call
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(uri, null, Integer.class).getBody();
    }
}
