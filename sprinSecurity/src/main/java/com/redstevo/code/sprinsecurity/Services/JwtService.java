package com.redstevo.code.sprinsecurity.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hibernate.annotations.SecondaryRow;
import org.hibernate.boot.archive.scan.internal.ScanResultImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.codec.Decoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.key}")
    private String secreteKey;
    public String generateToken(String username){
       return Jwts
                .builder()
                .subject(username)
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


}
