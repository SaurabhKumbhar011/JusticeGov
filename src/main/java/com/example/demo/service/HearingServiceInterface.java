package com.example.demo.service;

import com.example.demo.model.Hearing;
import com.example.demo.model.Proceeding; // Added import for Proceedings
import com.example.demo.model.enums.HearingStatus;

import java.util.List;

/**
 * Service Interface for Module 4.4: Hearing Scheduling & Court Proceedings[cite: 53].
 * Provides methods to manage the judicial calendar and record session outcomes[cite: 54].
 * @author JusticeGov Architect
 */
public interface HearingServiceInterface {
    
    /** * Schedules a new hearing after checking for judge conflicts[cite: 55]. 
     */
    Hearing scheduleHearing(Hearing hearing);
    
    /** * Retrieves the full list of hearings for a specific case[cite: 49]. 
     */
    List<Hearing> getCaseHistory(Long caseId);
    
    /** * Updates the status of an existing hearing (e.g., Adjourned, Completed)[cite: 57, 59]. 
     */
    Hearing updateHearingStatus(Long id, String newStatus);

    /** * Records judicial notes and proceedings for a specific hearing.
     * This method maps the Proceeding to the parent Hearing ID to prevent data errors.
     */
    Proceeding saveProceeding(Long hearingId, Proceeding proceeding);

	/**
	 * Updates the status of a hearing.
	 */
	Hearing updateHearingStatus(Long id, HearingStatus newStatus);
}