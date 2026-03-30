package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Added for 201 status
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Hearing;
import com.example.demo.model.Proceeding;
import com.example.demo.service.HearingServiceInterface;

/**
 * REST Controller for Module 4.4: Hearing Scheduling & Court Proceedings.
 * Manages the judicial calendar and records session outcomes.
 */
@RestController
@RequestMapping("/api/hearings")
public class HearingController {

	private static final Logger logger = LoggerFactory.getLogger(HearingController.class);

	@Autowired
	private HearingServiceInterface hearingService;

	/**
	 * Feature: Schedule a new hearing. Updated to return 201 Created for better
	 * REST compliance.
	 */
	@PostMapping("/schedule")
	public ResponseEntity<Hearing> schedule(@RequestBody Hearing hearing) {
		logger.info("REST Request to schedule a new hearing for Case ID: {}", hearing.getCaseRef());

		// Using .status(HttpStatus.CREATED) instead of .ok()
		return ResponseEntity.status(HttpStatus.CREATED).body(hearingService.scheduleHearing(hearing));
	}

	/**
	 * Feature: View hearing history for a specific case.
	 */
	@GetMapping("/history/{caseId}")
	public ResponseEntity<List<Hearing>> getHistory(@PathVariable Long caseId) {
		logger.info("REST Request to fetch history for Case ID: {}", caseId);
		return ResponseEntity.ok(hearingService.getCaseHistory(caseId));
	}

	/**
	 * Feature: Update hearing status (e.g., Completed, Adjourned).
	 */
	@PatchMapping("/{id}/status")
	public ResponseEntity<Hearing> updateStatus(@PathVariable Long id, @RequestParam String status) {
		logger.info("REST Request to update status for Hearing ID: {} to {}", id, status);
		return ResponseEntity.ok(hearingService.updateHearingStatus(id, status));
	}

	/**
	 * Feature: Record judicial notes (proceedings) for a specific hearing. Updated
	 * to return 200 Ok as a new record is being archived.
	 */
	@PostMapping("/{id}/proceedings")
	public ResponseEntity<Proceeding> recordProceeding(@PathVariable Long id, @RequestBody Proceeding proceeding) {
		logger.info("REST Request to record proceedings for Hearing ID: {}", id);

		// Using .status(HttpStatus.CREATED) for record archiving
		return ResponseEntity.status(HttpStatus.CREATED).body(hearingService.saveProceeding(id, proceeding));
	}
}