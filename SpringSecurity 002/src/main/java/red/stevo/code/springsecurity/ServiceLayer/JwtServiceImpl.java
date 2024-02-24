package red.stevo.code.springsecurity.ServiceLayer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService{

    @Override
    public Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parser()
                .decryptWith(getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    @Override
    public <T> T extractClaim(String jwtToken, Function<Claims, T> resolveClaim) {
        Claims claims = extractAllClaims(jwtToken);
        return resolveClaim.apply(claims);
    }


    @Override
    public Boolean isValid(String jwtToken, UserDetails userDetails) {
        return extractUsername(jwtToken).equals(userDetails.getUsername()) && !isExpired(jwtToken);
    }

    @Override
    public Boolean isExpired(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration).
                before(new Date(System.currentTimeMillis()));
    }

    @Override
    public SecretKey getKey() {
        byte[] bytes = Decoders.BASE64URL.decode(SECRETE_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    @Override
    public String generateJwtToken(String username) {
        log.info("Generating a jwt token");
        return Jwts
                .builder()
                .subject(username)
                .signWith(getKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 3*1000*60))
                .compact();
    }

    @Override
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }
}
