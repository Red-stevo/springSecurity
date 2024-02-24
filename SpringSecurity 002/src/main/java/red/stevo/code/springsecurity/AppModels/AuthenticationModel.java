package red.stevo.code.springsecurity.AppModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationModel {

    private String message;

    private Date date;

    private HttpStatus httpStatus;

    private String jwtToken;
}
