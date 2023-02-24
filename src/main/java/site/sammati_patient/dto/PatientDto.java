package site.sammati_patient.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private Integer patientId;
    private String firstName;
    private String LastName;
    private String phoneNumber;
    private String gender;
    private String UID_Number;
    private String UID_type;
    private String email;
    private String password;
    private Date DOB;
    private Date registrationDate;
    private String state;
    private String address;
    private String pinCode;
    private String passPhoto;
}
