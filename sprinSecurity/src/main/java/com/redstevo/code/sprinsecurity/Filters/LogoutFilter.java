package com.redstevo.code.sprinsecurity.Filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutFilter implements LogoutHandler {
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

        /*Mark the token a logout.*/
    }
}
