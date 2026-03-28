package com.example.demo.model;

import java.time.LocalDate;

import com.example.demo.model.enums.JudgementStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "Judgement", indexes = { @Index(name = "idx_judgement_case", columnList = "CaseID"),
		@Index(name = "idx_judgement_judge", columnList = "JudgeID") })
public class Judgement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "JudgementID")
	private Long id;

	@ManyToOne(optional = false) //Many judgements → one Case
	@JoinColumn(name = "CaseID", nullable = false)
	@ToString.Exclude
	private Case caseRef;

	@ManyToOne(optional = false) //Many judgements → one Judge
	@JoinColumn(name = "JudgeID", nullable = false)
	private User judge;

	@Lob
	@Column(name = "Summary", nullable = false)
	private String summary;

	@Column(name = "Date", nullable = false)
	private LocalDate date;

	@Enumerated(EnumType.STRING)
	@Column(name = "Status", nullable = false, length = 50)
	private JudgementStatus status = JudgementStatus.ISSUED;
}
