package com.redstevo.code.sprinsecurity.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseModel {

    private Long userId;

    private String username;

    private String jwt;

    private String message;

}
