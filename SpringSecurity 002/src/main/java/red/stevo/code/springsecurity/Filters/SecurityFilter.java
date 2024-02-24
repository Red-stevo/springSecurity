package red.stevo.code.springsecurity.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import red.stevo.code.springsecurity.ServiceLayer.AppService;
import red.stevo.code.springsecurity.ServiceLayer.JwtService;

import java.io.IOException;
@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        /*Getting the user's header*/
        String header = request.getHeader("Authorization");

        if(header == null || header.isBlank() || !header.startsWith("Bearer "))
        {
            log.info("Request Does Not Contain A jwt Token, Forwarding the request to the next filter chain");

        }
    }
}

