package com.justicegov.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.justicegov.demo.model.enums.GenericStatus;
import com.justicegov.demo.model.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@ToString(exclude = "auditLogs")
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long id;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false, length = 50)
    private Role role;

    @Column(name = "Email", nullable = false, length = 200, unique = true)
    private String email;

    @Column(name = "Phone", length = 50)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private GenericStatus status = GenericStatus.ACTIVE;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AuditLog> auditLogs = new ArrayList<>();
}