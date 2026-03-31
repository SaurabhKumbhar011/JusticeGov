package com.justicegov.demo.model;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.OrderStatus;

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
@Builder
@Entity
@Table(name = "CourtOrder", indexes = { @Index(name = "idx_order_case", columnList = "CaseID"),
		@Index(name = "idx_order_judge", columnList = "JudgeID") })
public class CourtOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OrderID")
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "CaseID", nullable = false)
	@ToString.Exclude
	private Case caseRef;

	@ManyToOne(optional = false)
	@JoinColumn(name = "JudgeID", nullable = false)
	private User judge;

	@Lob
	@Column(name = "Description", nullable = false)
	private String description;

	@Column(name = "Date", nullable = false)
	private LocalDate date;

	@Enumerated(EnumType.STRING)
	@Column(name = "Status", nullable = false, length = 50)
	private OrderStatus status = OrderStatus.ISSUED;
}