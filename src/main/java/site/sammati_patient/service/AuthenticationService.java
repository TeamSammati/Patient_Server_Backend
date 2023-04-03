package site.sammati_patient.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.sammati_patient.conf.JwtService;
import site.sammati_patient.dto.PatientDto;
import site.sammati_patient.dto.PatientLoginDto;
import site.sammati_patient.entity.Patient;
import site.sammati_patient.repository.PatientRepository;
import site.sammati_patient.repository.TokenRepository;
import site.sammati_patient.token.Token;
import site.sammati_patient.token.TokenType;
import site.sammati_patient.util.AuthenticationResponse;
import site.sammati_patient.util.Role;

import java.sql.Date;
import java.time.LocalDate;

import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Base64;
import javax.imageio.ImageIO;
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PatientRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;
    public AuthenticationResponse register(PatientDto request) {

        var patient= Patient.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .email(request.getEmail())
                .DOB(request.getDOB())
                .address(request.getAddress())
                .pinCode(request.getPinCode())
                .role(Role.USER)
                .state(request.getState())
                .registrationDate(Date.valueOf(LocalDate.now()))
                .UID_Number(request.getUidNumber())
                .UID_type(request.getUidType())
                .passPhoto(request.getPassPhoto())
                .userName(request.getUserName())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .build();

        var savedUser=repository.save(patient);
        var jwtToken=jwtService.generateToken(patient);
        String data = String.valueOf(savedUser.getPatientId());
        String path = "./target/qr/"+data+".png";

        try {
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(data, BarcodeFormat.QR_CODE, 500, 500);

            MatrixToImageWriter.writeToPath(matrix, "png", Paths.get(path));

            byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            encodedString="data:image/png;base64,"+encodedString;
            System.out.println(encodedString);
            System.out.println(patient.getPatientId());
            repository.addQR(encodedString,patient.getPatientId());
        }catch (Exception e){
            e.printStackTrace();
        }

//        savedUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }




    public AuthenticationResponse authenticate(PatientLoginDto request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUserName(),
                    request.getPassword()
            )
    );
    var user = repository.findByUserName(request.getUserName())
            .orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        revokeAllUserTokens(user);
        savedUserToken(user,jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(Patient patient){
        var validUserToken = tokenRepository.findAllValidTokenByUser(patient.getPatientId());
        if(validUserToken.isEmpty())
            return;
        validUserToken.forEach(t -> {
                t.setExpired(true);
                t.setRevoked(true);
            });
        tokenRepository.saveAll(validUserToken);

    }

    private void savedUserToken(Patient user, String jwtToken) {
        var token= Token.builder()
                .patient(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();

        tokenRepository.save(token);
    }
}
