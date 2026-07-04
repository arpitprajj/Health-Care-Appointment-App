package com.hca.auth_service.service.impl;

import com.hca.auth_service.dto.AuthResponse;
import com.hca.auth_service.dto.LoginRequest;
import com.hca.auth_service.dto.RegisterRequest;
import com.hca.auth_service.dto.UserRequest;
import com.hca.auth_service.entity.User;
import com.hca.auth_service.exception.EmailAlreadyExistException;
import com.hca.auth_service.feignClient.DoctorClient;
import com.hca.auth_service.feignClient.PatientClient;
import com.hca.auth_service.repository.UserRepository;
import com.hca.auth_service.service.AuthService;
import com.hca.auth_service.util.JwtUtil;
import com.hca.auth_service.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authManager;

    private final JwtUtil jwtUtil;
    private  final PatientClient patientClient;
    private final DoctorClient doctorClient;

    @Override
    public AuthResponse register(
            RegisterRequest request) {

        if (repository.existsByEmail(
                request.getEmail())) {

            throw new EmailAlreadyExistException(
                    "Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .phoneNumber(
                        request.getPhoneNumber())
                .password(
                        encoder.encode(
                                request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .emailVerified(false)
                .phoneVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        user = repository.save(user);
        if(user.getRole()== Role.PATIENT){
            UserRequest userRequest=new UserRequest();
            userRequest.setUserId(String.valueOf(user.getUserId()));
            userRequest.setEmail(user.getEmail());
            userRequest.setPhoneNumber(user.getPhoneNumber());
            patientClient.createPatient(userRequest);

        }
        else if(user.getRole()== Role.DOCTOR){
            UserRequest userRequest=new UserRequest();
            userRequest.setUserId(String.valueOf(user.getUserId()));
            userRequest.setEmail(user.getEmail());
            userRequest.setPhoneNumber(user.getPhoneNumber());
            doctorClient.createDoctor(userRequest);

        }

        String token =
                jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthResponse login(
            LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user =
                repository.findByEmail(
                                request.getEmail())
                        .orElseThrow(()->new RuntimeException("user not found"));

        String token =
                jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}