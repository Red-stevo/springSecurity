package com.redstevo.code.sprinsecurity.Services;

import com.redstevo.code.sprinsecurity.CustomExceptions.UserExistException;
import com.redstevo.code.sprinsecurity.Entities.AuthenticationEntity;
import com.redstevo.code.sprinsecurity.Models.AuthenticationRequestModel;
import com.redstevo.code.sprinsecurity.Models.AuthenticationResponseModel;
import com.redstevo.code.sprinsecurity.Repositories.AuthenticationRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsManager {

    private final AuthenticationRepository authenticationRepository;

    private AuthenticationResponseModel authenticationResponseModel;
    public ResponseEntity<AuthenticationResponseModel> register(AuthenticationRequestModel authenticationRequestModel){

        //setting up the userDetails object.
        AuthenticationEntity authentication = new AuthenticationEntity();
        authentication
                .setUsername(authenticationRequestModel.getUsername())
                .setPassword(authenticationRequestModel.getPassword())
                .setRole(authentication.getRole())
                .build();
        /*Check if the Username is already used*/
        if(userExists(authenticationRequestModel.getUsername())){
          throw  new UserExistException("Username is Already Used");
        }

        //forwarding the request to the service layer.
        createUser(authentication);

        //find the user.
        AuthenticationEntity authenticationEntity =
                authenticationRepository.findByUsername(authentication.getUsername()).orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found")
                );
        //prepare the user response
        authenticationRequestModel = new AuthenticationRequestModel();
        authenticationResponseModel.setJwt();
        authenticationResponseModel.setUsername(authenticationEntity.getUsername());
        authenticationResponseModel.setMessage("Sing Up Successful");
        authenticationResponseModel.setUserId(authenticationEntity.getId());

    }
    @Override
    public void createUser(UserDetails user) {

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
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not registered"));
    }
}
