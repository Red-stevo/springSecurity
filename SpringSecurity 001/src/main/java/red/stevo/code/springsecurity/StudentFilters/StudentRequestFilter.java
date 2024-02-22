package red.stevo.code.springsecurity.StudentFilters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import red.stevo.code.springsecurity.StudentService.StudentJwtService;

import java.io.IOException;

@Component
public class StudentRequestFilter extends OncePerRequestFilter {

    private final StudentJwtService studentJwtService;

    private final UserDetailsService userDetailsService;

    @Autowired
    public StudentRequestFilter(StudentJwtService studentJwtService, UserDetailsService userDetailsService) {
        this.studentJwtService = studentJwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String requestHeader = request.getHeader("Authentication");

        if(requestHeader == null || !requestHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        /*Extracting tje jwt token*/
        String jwtToken = requestHeader.substring(7);

        /*Getting the username*/
        String userName = studentJwtService.getUserName(jwtToken);

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);


            if (studentJwtService.isValid(userDetails, jwtToken)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                token.setDetails(
                        new WebAuthenticationDetailsSource()
                );

                SecurityContextHolder.getContext().setAuthentication(token);
            }
            filterChain.doFilter(request, response);
        }
    }
}
