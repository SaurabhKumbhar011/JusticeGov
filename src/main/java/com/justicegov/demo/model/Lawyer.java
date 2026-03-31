package com.justicegov.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.justicegov.demo.model.enums.GenericStatus;

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
@ToString(exclude = "documents")
@Entity
@Table(name = "Lawyer")
public class Lawyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LawyerID")
    private Long id;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "BarID", nullable = false, length = 100, unique = true)
    private String barId;

    @Column(name = "ContactInfo", length = 200)
    private String contactInfo;
    
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private GenericStatus status = GenericStatus.ACTIVE;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "LastModifiedDate")
    private LocalDateTime lastModifiedDate;
    
    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LegalDocument> documents = new ArrayList<>();
}