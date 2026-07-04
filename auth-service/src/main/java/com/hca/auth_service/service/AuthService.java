package com.hca.auth_service.service;

import com.hca.auth_service.dto.AuthResponse;
import com.hca.auth_service.dto.LoginRequest;
import com.hca.auth_service.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    AuthResponse register(
            RegisterRequest request);

    AuthResponse login(
            LoginRequest request);
}