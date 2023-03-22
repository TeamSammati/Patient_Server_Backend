package site.sammati_patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.sammati_patient.token.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    @Query("""
    select t from Token t inner join Patient u on t.patient.patientId=u.patientId
    where u.patientId=:patientId and (t.expired=false or t.revoked=false)
""")
    List<Token> findAllValidTokenByUser(Integer patientId);

    Optional<Token> findByToken(String token);
}
