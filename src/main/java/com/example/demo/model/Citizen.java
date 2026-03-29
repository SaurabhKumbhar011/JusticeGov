package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.enums.Gender;
import com.example.demo.model.enums.GenericStatus;

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
@Table(name = "Citizen")
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CitizenID")
    private Long id;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "DOB")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "Gender", length = 20)
    private Gender gender;

    @Column(name = "Address", length = 500)
    private String address;

    @Column(name = "ContactInfo", length = 200)
    private String contactInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 50)
    private GenericStatus status = GenericStatus.ACTIVE;
    
    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "LastModifiedDate")
    private LocalDateTime lastModifiedDate;

    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LegalDocument> documents = new ArrayList<>();
}