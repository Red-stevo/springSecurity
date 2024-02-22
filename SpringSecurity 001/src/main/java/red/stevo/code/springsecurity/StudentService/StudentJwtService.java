package red.stevo.code.springsecurity.StudentService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class StudentJwtService {

    private final String SECRETE_KEY = "6ce44ec4abc6a0e80dd954e30562f93e9bbe5baae71af990e08e56e046d35e19";

    public String generateJwtToken(String userName){
        return Jwts
                .builder()
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64URL.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String getUserName(String jwtToken){
     return getClaim(jwtToken, Claims::getSubject);
    }

    public Claims getAllClaims(String jwtToken){
        return Jwts
                .parser()
                .decryptWith(getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    private <T> T getClaim(String jwtToken, Function<Claims, T> extractClaim)
    {
        Claims claims = getAllClaims(jwtToken);
        return extractClaim.apply(claims);
    }

    public Boolean isValid(UserDetails userDetails, String jwtToken){
        return userDetails.getUsername().equals(getUserName(jwtToken))
                &&
                !isExpired(jwtToken);
    }

    private boolean isExpired(String jwtToken) {

        Date expirationDate = getClaim(jwtToken, Claims::getExpiration);

        return expirationDate.after(new Date(System.currentTimeMillis()));
    }


}
