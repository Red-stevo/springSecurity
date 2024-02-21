package red.stevo.code.springsecurity.StudentService;


import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class JwtService{
    public String extractUserName(String jwtToken)
    {
        return null;
    }

    public <T, Claim> T extractClaim(String jwtToken, Function<Claim, T> getAClaim){
        return  null;
    }
}
