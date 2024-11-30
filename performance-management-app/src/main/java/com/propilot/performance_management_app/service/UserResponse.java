package com.propilot.performance_management_app.service;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String token;
}
