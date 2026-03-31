package com.justicegov.demo.dto;

import java.time.LocalDate;

/**
 * DTO for managing legal research initiatives[cite: 72].
 * @author JusticeGov Architect
 */
public class ResearchProjectDTO {
    private String title;
    private String description;
    private Long researcherId;
    private LocalDate startDate;
    private LocalDate endDate;

    // --- Getters and Setters ---
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getResearcherId() { return researcherId; }
    public void setResearcherId(Long researcherId) { this.researcherId = researcherId; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}