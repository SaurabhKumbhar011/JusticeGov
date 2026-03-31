package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for scheduling and tracking hearings[cite: 58].
 * 
 * @author JusticeGov Architect
 */
public class HearingDTO {
	private Long caseId;
	private Long judgeId;
	private LocalDate date;
	private LocalTime time;
	private String status; // Scheduled, Completed, Adjourned [cite: 59]

	// --- Getters and Setters ---
	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public Long getJudgeId() {
		return judgeId;
	}

	public void setJudgeId(Long judgeId) {
		this.judgeId = judgeId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}