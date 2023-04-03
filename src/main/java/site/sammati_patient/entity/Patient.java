package site.sammati_patient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.sammati_patient.token.Token;
import site.sammati_patient.util.Role;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient implements UserDetails {
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
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String gender;
    @Column(unique = true)
    private String UID_Number;
    private String UID_type;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private Date DOB;
//   @Temporal(TemporalType.DATE)
    private Date registrationDate;
//    @PrePersist
//    private void onCreate(){
////        LocalDate localDate= LocalDate.now();
//        registrationDate= LocalDate.now();
//    }

    private String state;

    @Column(nullable = false, unique = true)
    private String userName;

    private String address;

    private String pinCode;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String passPhoto;

    @Column(columnDefinition = "TEXT")
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "patient",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
