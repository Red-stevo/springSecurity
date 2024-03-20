package com.redstevo.code.sprinsecurity.CustomExceptions;

public class UserExistException extends RuntimeException{
    public UserExistException(String message) {
        super(message);
    }
}
