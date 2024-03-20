package com.redstevo.code.sprinsecurity.Filters;

import com.redstevo.code.sprinsecurity.Entities.Tokens;
import com.redstevo.code.sprinsecurity.Repositories.TokenRepository;
import io.jsonwebtoken.lang.Assert;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LogoutFilter implements LogoutHandler {

    private final TokenRepository tokenRepository;
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {

        /*Getting the header.*/
        String requestHeader = request.getHeader("Authorization");

        if(requestHeader == null || requestHeader.startsWith("Bearer ")){
            return;
        }

        /*Extracting the tokens*/
        String jwt = requestHeader.substring(7);

        /*Get token for update.*/
        List<Tokens> tokens = tokenRepository.findByTokenAndIsLoggedOut(jwt, false).orElse(null);

        /*Updating the isLoggedOut status of all tokens to true*/
        if(tokens != null){
            tokens.forEach((token) ->{
                token.setIsLoggedOut(true);
            });
            tokenRepository.saveAll(tokens);
        }
    }
}
