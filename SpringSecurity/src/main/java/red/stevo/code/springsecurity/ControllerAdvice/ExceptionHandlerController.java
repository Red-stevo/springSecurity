package red.stevo.code.springsecurity.ControllerAdvice;

import ch.qos.logback.core.model.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import red.stevo.code.springsecurity.Models.ExceptionModel;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionModel> handlerBadCredentialsException(BadCredentialsException e)
    {
        ExceptionModel exceptionModel = new ExceptionModel();

        exceptionModel.setDate(new Date());
        exceptionModel.setMessage("The password or username are incorrect");
        exceptionModel.setHttpStatus(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(exceptionModel,HttpStatus.UNAUTHORIZED);
    }
}
