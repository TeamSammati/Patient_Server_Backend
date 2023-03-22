package site.sammati_patient.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private static final Integer EXPIRE_MIN = 5;
    private static LoadingCache<String, Integer> otpCache;

    public OtpService()
    {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    public static Integer generateOTP(String key)
    {
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        otpCache.put(key, OTP);

        return OTP;
    }

    public static String getOPTByKey(String key)
    {
        Integer otp = otpCache.getIfPresent(key);
        if(otp==null)
            return "-99";
        else return otp.toString();
    }

    public static void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }

    public static String OTP(int len) {
        System.out.println("Generating OTP using random() : ");
        System.out.print("You OTP is : ");

        String numbers = "0123456789";
        Random rndm_method = new Random();

        String otp = "";

        for (int i = 0; i < len; i++) {
            otp += numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return otp;
    }
    public static Integer sendOTP(String phno, String message) {
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