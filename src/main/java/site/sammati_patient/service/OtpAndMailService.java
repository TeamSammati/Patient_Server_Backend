package site.sammati_patient.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpAndMailService {
    private static final Integer EXPIRE_MIN = 5;
    private static LoadingCache<String, Integer> otpCache;

    public OtpAndMailService()
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
            String myMessage = "Hello there, "+message+ " is your one time password (OTP) to register in to Sammati.site";
            String myUrl="https://www.fast2sms.com/dev/bulk?authorization="+apiKey+"&sender_id="+sendId+"&message="+myMessage+"&language="+language+"&route="+route+"&numbers="+phno;

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

    public static void sendEmail(String to, Integer pid, String name) {

        String from = "alerts@sammati.site";
        String subject = "Sammati : Confirmation";
        String host="smtppro.zoho.in";

        Properties properties = System.getProperties();
        System.out.println("PROPERTIES "+properties);

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("alerts@sammati.site", "hkuySpsxDiia");
            }
        });

        session.setDebug(true);

        MimeMessage m = new MimeMessage(session);

        try {
            m.setFrom(from);

            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            m.setSubject(subject);

            m.setContent("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> <html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"> <head> <!--[if gte mso 9]> <xml> <o:OfficeDocumentSettings> <o:AllowPNG/> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml> <![endif]--> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <meta name=\"x-apple-disable-message-reformatting\"> <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]--> <title></title> <style type=\"text/css\"> a { color: #0000ee; text-decoration: underline; } @media only screen and (min-width: 620px) { .u-row { width: 600px !important; } .u-row .u-col { vertical-align: top; } .u-row .u-col-100 { width: 600px !important; } } @media (max-width: 620px) { .u-row-container { max-width: 100% !important; padding-left: 0px !important; padding-right: 0px !important; } .u-row .u-col { min-width: 320px !important; max-width: 100% !important; display: block !important; } .u-row { width: calc(100% - 40px) !important; } .u-col { width: 100% !important; } .u-col > div { margin: 0 auto; } } body { margin: 0; padding: 0; } table, tr, td { vertical-align: top; border-collapse: collapse; } p { margin: 0; } .ie-container table, .mso-container table { table-layout: fixed; } * { line-height: inherit; } a[x-apple-data-detectors='true'] { color: inherit !important; text-decoration: none !important; } </style> <!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Montserrat:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]--> </head> <body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f0f0f0\"> <!--[if IE]><div class=\"ie-container\"><![endif]--> <!--[if mso]><div class=\"mso-container\"><![endif]--> <table style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f0f0f0;width:100%\" cellpadding=\"0\" cellspacing=\"0\"> <tbody> <tr style=\"vertical-align: top\"> <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\"> <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f0f0f0;\"><![endif]--> <div class=\"u-row-container\" style=\"padding: 0px 10px 1px;background-color: #ffffff\"> <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\"> <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\"> <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px 10px 1px;background-color: #ffffff;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]--> <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]--> <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\"> <div style=\"width: 100% !important;\"> <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]--> <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"> <tbody> <tr> <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 20px;font-family:'Montserrat',sans-serif;\" align=\"left\"> <div style=\"color: #b0b436; line-height: 120%; text-align: center; word-wrap: break-word;\"> <p style=\"font-size: 14px; line-height: 120%;\"><span style=\"font-family: 'times new roman', times; font-size: 52px; line-height: 86.4px; color: #b0b436;\">Welcome to Sammati!</span></p> </div> </td> </tr> </tbody> </table> <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]--> </div> </div> <!--[if (mso)|(IE)]></td><![endif]--> <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]--> </div> </div> </div> <div class=\"u-row-container\" style=\"padding: 0px;background-color: #ffffff\"> <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\"> <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\"> <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #ffffff;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]--> <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]--> <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\"> <div style=\"width: 100% !important;\"> <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]--> <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"> <tbody> <tr> <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px 20px;font-family:'Montserrat',sans-serif;\" align=\"left\"> <div style=\"color: #3d3d3d; line-height: 120%; text-align: center; word-wrap: break-word;\"> <p style=\"font-size: 14px; line-height: 120%;\"><span style=\"font-family: 'times new roman', times; font-size: 30px; line-height: 36px; color: #8a536a;\">sammati.site</span></p> </div> </td> </tr> </tbody> </table> <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"> <tbody> <tr> <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 20px 20px;font-family:'Montserrat',sans-serif;\" align=\"left\"> <div style=\"color: #3d3d3d; line-height: 150%; text-align: center; word-wrap: break-word;\"> <p style=\"font-size: 14px; line-height: 150%;\"><span style=\"color: #8a536a; font-size: 14px; line-height: 21px;\">Where your Health data flows only with your 'Sammati'</span></p> </div> </td> </tr> </tbody> </table> </td> </tr> </tbody> </table> <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]--> </div> </div> <!--[if (mso)|(IE)]></td><![endif]--> <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]--> </div> </div> </div> <div class=\"u-row-container\" style=\"padding: 10px 10px 5px;background-color: #ffffff\"> <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\"> <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\"> <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 10px 10px 5px;background-color: #ffffff;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]--> <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]--> <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\"> <div style=\"width: 100% !important;\"> <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]--> <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"> <tbody> <tr> <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px;font-family:'Montserrat',sans-serif;\" align=\"left\"> <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #CCC;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\"> <tbody> <tr style=\"vertical-align: top\"> <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\"> <span>&#160;</span> </td> </tr> </tbody> </table> </td> </tr> </tbody> </table> <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"> <tbody> <tr> <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px 20px;font-family:'Montserrat',sans-serif;\" align=\"left\"> <div style=\"color: #000; line-height: 120%; text-align: center; word-wrap: break-word;\"> <p style=\"font-size: 14px; line-height: 120%;\"><span style=\"color: #8a536a; font-size: 14px; line-height: 16.8px;\">Hello, "+name+"<br><br>Your Patient ID is " + pid + "</span></p> </div> </td> </tr> </tbody> </table> <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"> <tbody> <tr> <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px;font-family:'Montserrat',sans-serif;\" align=\"left\"> <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #CCC;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\"> <tbody> <tr style=\"vertical-align: top\"> <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\"> <span>&#160;</span> </td> </tr> </tbody> </table> </td> </tr> </tbody> </table> <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]--> </div> </div> <!--[if (mso)|(IE)]></td><![endif]--> <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]--> </div> </div> </div> <div class=\"u-row-container\" style=\"padding: 5px 10px;background-color: #ffffff\"> <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\"> <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\"> <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 5px 10px;background-color: #ffffff;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]--> <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]--> <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\"> <div style=\"width: 100% !important;\"> <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]--> <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"> <tbody> <tr> <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px 20px;font-family:'Montserrat',sans-serif;\" align=\"left\"> <div style=\"color: #000; line-height: 150%; text-align: center; word-wrap: break-word;\"> <p style=\"font-size: 14px; line-height: 150%;\"><span style=\"color: #8a536a; font-size: 14px; line-height: 21px;\">Sammati lets you get your Health Data, Manage your Consent all at one place</span></p> </div> </td> </tr> </tbody> </table> <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]--> </div> </div> <!--[if (mso)|(IE)]></td><![endif]--> <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]--> </div> </div> </div> <div class=\"u-row-container\" style=\"padding: 0px;background-color: #ffffff\"> <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\"> <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\"> <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #ffffff;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]--> <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]--> <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\"> <div style=\"width: 100% !important;\"> <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]--> <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"> <tbody> <tr> <td style=\"overflow-wrap:break-word;word-break:break-word;padding:20px 20px 35px;font-family:'Montserrat',sans-serif;\" align=\"left\"> <div align=\"center\"> <!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:'Montserrat',sans-serif;\"><tr><td style=\"font-family:'Montserrat',sans-serif;\" align=\"center\"><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"\" style=\"height:56px; v-text-anchor:middle; width:198px;\" arcsize=\"0%\" stroke=\"f\" fillcolor=\"#b0b436\"><w:anchorlock/><center style=\"color:#FFF;font-family:'Montserrat',sans-serif;\"><![endif]--> <a href=\"\" target=\"_blank\" style=\"box-sizing: border-box;display: inline-block;font-family:'Montserrat',sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFF; background-color: #b0b436; border-radius: 0px; -webkit-border-radius: 0px; -moz-border-radius: 0px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;\"> <span style=\"display:block;padding:10px 30px;line-height:150%;\"><span style=\"font-family: 'times new roman', times; font-size: 24px; line-height: 36px;\">LOGIN NOW</span></span> </a> <!--[if mso]></center></v:roundrect></td></tr></table><![endif]--> </div> </td> </tr> </tbody> </table> <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]--> </div> </div> <!--[if (mso)|(IE)]></td><![endif]--> <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]--> </div> </div> </div> <div class=\"u-row-container\" style=\"padding: 30px;background-color: #f0f0f0\"> <div class=\"u-row\" style=\"Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\"> <div style=\"border-collapse: collapse;display: table;width: 100%;background-color: transparent;\"> <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 30px;background-color: #f0f0f0;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: transparent;\"><![endif]--> <!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]--> <div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\"> <div style=\"width: 100% !important;\"> <!--[if (!mso)&(!IE)]><!--><div style=\"padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]--> <table style=\"font-family:'Montserrat',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"> <tbody> <tr> <td style=\"overflow-wrap:break-word;word-break:break-word;padding:20px;font-family:'Montserrat',sans-serif;\" align=\"left\"> <div style=\"color: #000; line-height: 120%; text-align: left; word-wrap: break-word;\"> <div style=\"font-family: arial, helvetica, sans-serif;\"><span style=\"font-size: 12px; color: #8a536a; line-height: 14.4px;\"><center>You received this email because you signed up on sammati.site</center></span></div> </div> </td> </tr> </tbody> </table> <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]--> </div> </div> <!--[if (mso)|(IE)]></td><![endif]--> <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]--> </div> </div> </div> <!--[if (mso)|(IE)]></td></tr></table><![endif]--> </td> </tr> </tbody> </table> <!--[if mso]></div><![endif]--> <!--[if IE]></div><![endif]--> </body> </html>","text/html" );

            Transport.send(m);

            System.out.println("Email Sent success");


        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}