package com.propilot.performance_management_app.service;


import com.propilot.performance_management_app.model.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {

    private UserResponse user;
    private String token;
}