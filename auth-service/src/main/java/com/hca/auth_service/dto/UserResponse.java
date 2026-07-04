package com.hca.auth_service.dto;

import com.hca.auth_service.util.Role;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {

    private UUID userId;

    private String email;

    private String phoneNumber;

    private Role role;
}
