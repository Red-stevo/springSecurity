package red.stevo.code.springsecurity.StudentService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

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

    private Key getKey() {
        byte[] bytes = Decoders.BASE64URL.decode(SECRETE_KEY);

        return Keys.hmacShaKeyFor(bytes);
    }
}
