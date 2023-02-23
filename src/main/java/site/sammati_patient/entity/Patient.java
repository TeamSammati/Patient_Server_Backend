package site.sammati_patient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @SequenceGenerator(
            name="patient_id_sequence",
            sequenceName = "patient_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patient_id_sequence"
    )
    private Integer patientId;
    private String patientName;
    private String phoneNumber;
    private String gender;
    private String UID_Number;
    private String UID_type;
    private String email;
    private Date DOB;
    private Date registrationDate;
}
