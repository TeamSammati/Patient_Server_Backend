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
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String LastName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String gender;
    private String UID_Number;
    private String UID_type;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private Date DOB;
    private Date registrationDate;
}
