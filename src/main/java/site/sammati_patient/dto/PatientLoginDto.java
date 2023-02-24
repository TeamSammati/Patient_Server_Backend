package site.sammati_patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientLoginDto {
    private String phoneNumber;
    private String password;
}
