package com.redstevo.code.sprinsecurity.Controllers;

import com.redstevo.code.sprinsecurity.Entities.AuthenticationEntity;
import com.redstevo.code.sprinsecurity.Models.AuthenticationRequestModel;
import com.redstevo.code.sprinsecurity.Models.AuthenticationResponseModel;
import com.redstevo.code.sprinsecurity.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

/*Controller to receive user registration request and save the user to the database.*/
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponseModel> registerUser(
           @Validated @RequestBody AuthenticationRequestModel authenticationRequestModel
            ){
        //setting up the userDetails object.
        AuthenticationEntity authentication = new AuthenticationEntity();
        authentication
                .setUsername(authenticationRequestModel.getUsername())
                .setPassword(authenticationRequestModel.getPassword())
                .setRole(authentication.getRole())
                .build();

        //forwarding the request to the service layer.
        authenticationService.createUser(authentication);

        //prepare the user response

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
