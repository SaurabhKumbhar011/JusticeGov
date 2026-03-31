package com.justicegov.demo.model;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.ReportScope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReportID")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "Scope", nullable = false, length = 50)
	private ReportScope scope;

	@Lob
	@Column(name = "Metrics", nullable = false)
	private String metrics; // JSON/text payload

	@Column(name = "GeneratedDate", nullable = false)
	private LocalDate generatedDate;
}