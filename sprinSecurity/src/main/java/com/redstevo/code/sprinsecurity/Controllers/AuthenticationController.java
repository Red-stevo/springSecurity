package com.redstevo.code.sprinsecurity.Controllers;

import com.redstevo.code.sprinsecurity.Models.AuthenticationRequestModel;
import com.redstevo.code.sprinsecurity.Models.AuthenticationResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class AuthenticationController {


    public ResponseEntity<AuthenticationResponseModel> registerUser(
            @RequestBody AuthenticationRequestModel authenticationRequestModel
            ){

    }
}
