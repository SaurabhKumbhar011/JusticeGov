package com.justicegov.demo.model;

import java.time.LocalDateTime;

import com.justicegov.demo.model.enums.NotificationCategory;
import com.justicegov.demo.model.enums.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "Notification", indexes = {
        @Index(name = "idx_notification_user", columnList = "UserID"),
        @Index(name = "idx_notification_category", columnList = "Category")
})
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "EntityID")
    private Long entityId; // optional polymorphic link

    @Enumerated(EnumType.STRING)
    @Column(name = "Category", nullable = false, length = 50)
    private NotificationCategory category;

    @Column(name = "Message", nullable = false, length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private NotificationStatus status = NotificationStatus.PENDING;

    @Column(name = "CreatedDate", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}