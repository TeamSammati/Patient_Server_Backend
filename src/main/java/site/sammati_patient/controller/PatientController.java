package site.sammati_patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.service.PatientService;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

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


    @Autowired
    private PatientService patientService;
    @PostMapping("/save")
    public Integer savePatient(@RequestBody PatientDto patientDto){
        int id=patientService.addPatient(patientDto);
        return id;
    }

    @PostMapping("/login")
    public Patient loginPatient(@RequestBody PatientLoginDto patientDto){
//        System.out.printf(patientDto.getPhoneNumber());
        Patient patient=patientService.loginPatient(patientDto);
        return patient;
    }

    @PostMapping("/send_otp")
    public Integer sendOTP(@RequestParam("phoneNumber") String phno, @RequestParam("message") String message) {
        //Third party API call
        int code=-1;
        try
        {
            String apiKey="Tf0l5vJFUNs3EqRDwmVpjkIHLbYXSBtxGicuhyPQdW9nr2e178brnxh0MUt2DmlTGoPKJLEvaAYN9SW6";
            String sendId="FSTSMS";
            message= URLEncoder.encode(message, "UTF-8");
            String language="english";
            String route="p";
            String myUrl="https://www.fast2sms.com/dev/bulk?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+phno;

            URL url=new URL(myUrl);
            HttpsURLConnection con=(HttpsURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-cache");
            code=con.getResponseCode();
            StringBuffer response=new StringBuffer();
            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
            while(true)
            {
                String line=br.readLine();
                if(line==null)
                {
                    break;
                }
                response.append(line);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }
}
