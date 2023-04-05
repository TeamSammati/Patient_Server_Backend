package site.sammati_patient.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientOtpDto
{
    private Integer hospitalId;
    private Integer patientId;
    private String otp;
}
