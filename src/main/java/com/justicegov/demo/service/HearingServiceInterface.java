package com.justicegov.demo.service;

import java.util.List;

import com.justicegov.demo.model.Hearing;
import com.justicegov.demo.model.Proceeding;
import com.justicegov.demo.model.enums.HearingStatus;

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