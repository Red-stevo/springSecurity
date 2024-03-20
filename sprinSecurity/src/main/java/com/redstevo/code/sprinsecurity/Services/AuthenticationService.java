package com.redstevo.code.sprinsecurity.Services;

import com.redstevo.code.sprinsecurity.CustomExceptions.UserExistException;
import com.redstevo.code.sprinsecurity.Entities.AuthenticationEntity;
import com.redstevo.code.sprinsecurity.Entities.Tokens;
import com.redstevo.code.sprinsecurity.Models.AuthenticationRequestModel;
import com.redstevo.code.sprinsecurity.Models.AuthenticationResponseModel;
import com.redstevo.code.sprinsecurity.Repositories.AuthenticationRepository;
import com.redstevo.code.sprinsecurity.Repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsManager {

    private final AuthenticationRepository authenticationRepository;

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private AuthenticationResponseModel authenticationResponseModel;



    public ResponseEntity<AuthenticationResponseModel> register(AuthenticationRequestModel authenticationRequestModel){

        log.info("Processing the request");
        /*Check if the Username is already used*/
        if(userExists(authenticationRequestModel.getUsername())){
          throw  new UserExistException("Username is Already Used");
        }

        //setting up the userDetails object.
        AuthenticationEntity authentication = new AuthenticationEntity();
        authentication
                .setUsername(authenticationRequestModel.getUsername())
                .setPassword(authenticationRequestModel.getPassword())
                .setRole(authentication.getRole())
                .build();


        //forwarding the request to the service layer.
        createUser(authentication);

        //find the user.
        AuthenticationEntity authenticationEntity =
                authenticationRepository.findByUsername(authentication.getUsername()).orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found")
                );
        /*Generating a token for the user.*/
        String token = generateToken(authenticationEntity);

        /*Store the generated token*/
        storeToken(token, authenticationEntity);

        //prepare the user response
        authenticationResponseModel = new AuthenticationResponseModel();
        authenticationResponseModel.setJwt(token);
        authenticationResponseModel.setUsername(authenticationEntity.getUsername());
        authenticationResponseModel.setMessage("Sing Up Successful");
        authenticationResponseModel.setUserId(authenticationEntity.getId());


        return new ResponseEntity<>(authenticationResponseModel, HttpStatus.CREATED);
    }

    private void storeToken(String token, AuthenticationEntity authenticationEntity){
        Tokens tokens = new Tokens();

        tokens.setIsLoggedOut(false);
        tokens.setAuthentication(authenticationEntity);
        tokens.setToken(token);

        tokenRepository.save(tokens);
    }

    private String generateToken(AuthenticationEntity authentication){
        Map<String, Object> claims = new HashMap<>(2);

        /*Adding the claim for token generation*/
        claims.put("id", authentication.getId());
        claims.put("role", authentication.getRole());

        /*Calling method for token generation*/
        return jwtService.generateToken(authentication.getUsername(), claims);
    }

    @Override
    public void createUser(UserDetails user) {
        authenticationRepository.save(user);
        log.info("User Saved Successfully");
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return authenticationRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not registered"));
    }
}
