package site.sammati_patient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.sammati_patient.util.Role;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDetailsDto {
    private Integer patientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private String uidNumber;
    private String uidType;
    private String email;
    private Date DOB;
    private Date registrationDate;
    private String state;
    private String address;
    private String pinCode;
    private String passPhoto;
    private Role role;
}
