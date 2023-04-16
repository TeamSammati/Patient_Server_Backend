package site.sammati_patient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
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
public class PatientDto {
    private Integer patientId;
    private String firstName;
    private String LastName;
    private String phoneNumber;
    private String gender;
    private String uidNumber;
    private String uidType;
    private String email;
    private String password;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date DOB;
    private Date registrationDate;
    private String state;
    private String userName;
    private String address;
    private String pinCode;
    private String passPhoto;
    private Role role;

}
