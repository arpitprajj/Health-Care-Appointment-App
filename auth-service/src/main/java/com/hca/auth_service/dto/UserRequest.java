package com.hca.auth_service.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String userId;
    private String email;
    private String phoneNumber;
}
