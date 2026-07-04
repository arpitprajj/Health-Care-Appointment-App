package com.hca.auth_service.entity;

import com.hca.auth_service.util.Role;
import jakarta.persistence.*;
import lombok.*;



import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean enabled;

    private Boolean emailVerified;

    private Boolean phoneVerified;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
