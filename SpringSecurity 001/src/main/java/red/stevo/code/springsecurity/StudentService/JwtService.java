package red.stevo.code.springsecurity.StudentService;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import red.stevo.code.springsecurity.SchoolDTO.StudentDTO;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component

public class JwtService{

    private final String SECRETE_KEY = "f782fe5d90020063f51d691e7aca19c4245534013781aa8840b936005ac03d3f";


    /*This method enables us to get the subject from a jwtToken which
    * usually is the username*/
    public String extractUserName(String jwtToken)
    {
        return extractClaim(jwtToken,Claims::getSubject);
    }

    /*This method is responsible for checking whether the jwt token is
    * valid, or not.
    * It checks the username
    * And Confirms that the token is not expired*/
    public Boolean isValid(String jwtToken, UserDetails userDetails)
    {
        String userName = userDetails.getUsername();

        return extractUserName(jwtToken).equals(userName) && isExpired(jwtToken);
    }


    /*Method to check whether the jwt token is expired*/
    private Boolean isExpired(String jwtToken){
        return getExpirationDate(jwtToken).after(new Date(System.currentTimeMillis()));
    }

    /*Method to get the expiration from the payload*/
    private Date getExpirationDate(String jwtToken)
    {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /*This method takes in the jwt token
     * and enables us to get all claims(payload) from the jwtToken*/
    private Claims extractAllClaims(String jwtToken)
    {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    /*This method enables us to get individual claims from the provided payload
    * from the extractAllClaims method*/
    private  <T> T extractClaim(String jwtToken, Function<Claims, T> getAClaim){
        Claims claims = extractAllClaims(jwtToken);
        return  getAClaim.apply(claims);
    }


    /*This method will be user to generate a new jwt token to assign to the user.*/
    public String generateJwtToken(StudentDTO studentDTO){
        return Jwts
                .builder()
                .subject(studentDTO.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 2*60*1000))
                .signWith(getKey())
                .compact();
    }

    /*This method takes in the Base 64 encoded string decodes it and forwards it for encryption
    * to generate a key.*/
    private SecretKey getKey() {
        byte[] keyByte = Decoders.BASE64URL.decode(SECRETE_KEY);

        return Keys.hmacShaKeyFor(keyByte);
    }


}
