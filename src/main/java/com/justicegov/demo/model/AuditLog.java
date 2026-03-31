package com.justicegov.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "AuditLog", indexes = {
        @Index(name = "idx_audit_user", columnList = "UserID"),
        @Index(name = "idx_audit_time", columnList = "Timestamp")
})
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuditID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "Action", nullable = false, length = 200)
    private String action;

    @Column(name = "Resource", nullable = false, length = 200)
    private String resource;

    @Column(name = "Timestamp", nullable = false)
    private LocalDateTime timestamp;
}