package site.sammati_patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static site.sammati_patient.service.OtpAndMailService.sendEmail;

@SpringBootApplication
public class SammatiPatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SammatiPatientApplication.class, args);

	}

//	@Bean
//	public WebMvcConfigurer configureWeb() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry reg) {
//				reg.addMapping("/**").allowedOrigins("*");
//			}
//		};
//	}
}
