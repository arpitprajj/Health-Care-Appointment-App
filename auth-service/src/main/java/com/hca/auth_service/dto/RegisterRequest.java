package com.hca.auth_service.dto;

import com.hca.auth_service.util.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @Email
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String phoneNumber;

    @Size(min = 6)
    private String password;

    private Role role;
}