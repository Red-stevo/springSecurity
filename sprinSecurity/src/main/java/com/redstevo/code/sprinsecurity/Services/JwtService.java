package com.redstevo.code.sprinsecurity.Services;

import ch.qos.logback.core.subst.Token;
import com.redstevo.code.sprinsecurity.Entities.Tokens;
import com.redstevo.code.sprinsecurity.Repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service()
@RequiredArgsConstructor()
public class JwtService {

    private final TokenRepository tokenRepository;

    @Value("${security.key}")
    private String secreteKey;
    public String generateToken(String username, Map<String, ?> claims){
       return Jwts
                .builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] bytes = Decoders
                .BASE64URL
                .decode(secreteKey);
        return Keys
                .hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String jwt){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    private <T> T getClaim(Function<Claims, T> claimsExtractor, String jwt){
        Claims claims = extractAllClaims(jwt);
        return claimsExtractor.apply(claims);
    }

    public String getUsername(String jwt){
        return getClaim(Claims::getSubject, jwt);
    }

    public Boolean isValid(UserDetails userDetails, String jwt){

        Tokens tokens = tokenRepository.findByToken(jwt).orElseThrow();

        return (!isExpired(jwt)) && (getUsername(jwt).equals(userDetails.getUsername()) && (!tokens.getIsLoggedOut()));
    }

    private Date getExpiration(String jwt){
        return getClaim(Claims::getExpiration, jwt);
    }

    private Boolean isExpired(String jwt){
        return new  Date(System.currentTimeMillis()).after(getExpiration(jwt));
    }

}
