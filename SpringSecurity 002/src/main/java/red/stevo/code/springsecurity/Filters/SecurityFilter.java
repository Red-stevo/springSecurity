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

    private final JwtService jwtService;

    private final AppService appService;

    @Autowired
    public SecurityFilter(JwtService jwtService, AppService appService) {
        this.jwtService = jwtService;
        this.appService = appService;
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        /*Check where the request has a jwt token.*/
        log.info("fetching the JWT token");
        String header = request.getHeader("Authorization");

        if(header == null || header.startsWith("Bearer"))
        {
            log.info("Forwarding the request to the next filter chain");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = header.substring(7);

        /*Getting the username from the jwt*/
        String username = jwtService.extractUsername(jwtToken);

        if(username != null || SecurityContextHolder.getContext().getAuthentication() == null)
        {
            /*Getting the UserDetails Objects*/
            UserDetails userDetails = appService.loadUserByUsername(username);


            if(jwtService.isValid(jwtToken,userDetails))
            {
                /*Moving forward to create the userPasswordAuthentication Object*/
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                token.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(request,response);
    }
}
