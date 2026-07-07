package com.hca.notification_service.entity;

import com.hca.notification_service.enums.EventType;
import com.hca.notification_service.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID notificationId;

    private UUID appointmentId;

    private String recipientEmail;

    private String subject;

    @Column(length = 5000)
    private String message;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    private LocalDateTime createdAt;
}