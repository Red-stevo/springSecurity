package red.stevo.code.springsecurity.ServiceLayer;

import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.function.Function;

public interface JwtService {

    final  static  String SECRETE_KEY = "5795bc0b3859ad91f9e6d1fa1d463df7d7d752c24620e8d8ae1c9af40a647bdd";

    Claims extractAllClaims(String jwtToken);

    <T> T extractClaim(String jwtToken, Function<Claims, T> resolveClaim);

    Boolean isValid(String username, String jwtToken);

    Boolean isExpired(String jwtToken);

    SecretKey getKey();

    String generateJwtToken(String username);

    String extractUsername(String jwtToken);
}
