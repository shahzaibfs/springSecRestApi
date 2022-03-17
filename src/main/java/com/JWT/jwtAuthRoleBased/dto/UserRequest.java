package com.JWT.jwtAuthRoleBased.dto;


public class UserRequest {

    public String username, password;

    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
