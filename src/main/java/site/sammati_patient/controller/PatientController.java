package site.sammati_patient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import site.sammati_patient.service.PatientService;

import java.util.List;
import static site.sammati_patient.service.OtpService.*;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final static String HANSAL="172.16.133.184";
    private final static String RIZWANI="172.16.144.47";
    private final static String SOHAM="172.16.131.147";
    private final static String TUSHAR="";

    private final PatientService patientService;


    @GetMapping("Request_List")
    public List<Object> getConsentRequestByPid(@RequestParam("patientId") Integer patientId) {
        String uri = "http://"+HANSAL+":6969/Request_List/" + patientId;
        //IP of Sammati server/API call
        RestTemplate restTemplate = new RestTemplate();
        List<Object> result = restTemplate.getForObject(uri, List.class);
        return result;
    }

    @PostMapping("/response")
    public Integer generateResponse(@RequestParam("crid") Integer crid, @RequestParam("status") Integer status) {
        String uri = "http://"+HANSAL+":6969/response/" + crid + "/" + status;
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
    @PostMapping("/get_records")
    public List<Object> getRecords(@RequestParam Integer patientID,@RequestParam Integer reqType)
    {
        String uri = "http://"+HANSAL+":6979/handle_records?patientID="+patientID+"&"+"reqType="+reqType;
        //IP of Sammati server/API call
        System.out.println("sasasas");
        RestTemplate restTemplate = new RestTemplate();
        List<Object> result = restTemplate.getForObject(uri, List.class);
        System.out.println(result);
        return result;
    }
}
