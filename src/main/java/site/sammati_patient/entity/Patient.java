package site.sammati_patient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

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
    private String lastName;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String gender;
    @Column(unique = true)
    private String UID_Number;
    private String UID_type;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private Date DOB;
//    @Temporal(TemporalType.DATE)
    private Date registrationDate;
//    @PrePersist
//    private void onCreate(){
////        LocalDate localDate= LocalDate.now();
//        registrationDate= LocalDate.now();
//    }

    private String state;

    private String address;

    private String pinCode;

    private String passPhoto;

}
