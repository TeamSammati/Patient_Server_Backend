package site.sammati_patient.conf;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import site.sammati_patient.util.Role;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY="3778214125442A472D4B6150645367566B58703273357638792F423F4528482B";
    public String extractUserName(String token) {
    return extractClaims(token,Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails, Role role){
        return generateToken(new HashMap<>(),userDetails,role);
    }
    public String generateToken(Map<String,Object> extraClaims,
                                UserDetails userDetails,Role role){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .claim("authorities",new SimpleGrantedAuthority(role.name()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
