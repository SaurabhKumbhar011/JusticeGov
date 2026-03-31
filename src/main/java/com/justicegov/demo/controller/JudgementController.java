package com.justicegov.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justicegov.demo.dto.JudgementCreateRequest;
import com.justicegov.demo.dto.JudgementPatchRequest;
import com.justicegov.demo.dto.JudgementResponse;
import com.justicegov.demo.service.JudgementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class JudgementController {

	private final JudgementService judgementService;

	// POST /api/cases/{caseId}/judgements – create judgement
	@PostMapping(path = "/cases/{caseId}/judgements", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JudgementResponse> create(@PathVariable Long caseId,
			@Valid @RequestBody JudgementCreateRequest request) {

		log.info("POST /api/cases/{}/judgements called", caseId);

		JudgementResponse created = judgementService.create(caseId, request);

		log.info("Judgement created successfully with id={}", created.getId());

		return ResponseEntity.created(URI.create("/api/judgements/" + created.getId())).body(created);
	}

	// GET /api/judgements/{judgementId}
	@GetMapping("/judgements/{judgementId}")
	public JudgementResponse getById(@PathVariable Long judgementId) {

		log.info("GET /api/judgements/{} called", judgementId);

		return judgementService.getById(judgementId);
	}

	// PATCH /api/judgements/{judgementId}
	@PatchMapping(path = "/judgements/{judgementId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public JudgementResponse patch(@PathVariable Long judgementId, @RequestBody JudgementPatchRequest request) {

		log.info("PATCH /api/judgements/{} called", judgementId);

		return judgementService.patch(judgementId, request);
	}

	// GET /api/cases/{caseId}/judgements
	@GetMapping("/cases/{caseId}/judgements")
	public List<JudgementResponse> listByCase(@PathVariable Long caseId) {

		log.info("GET /api/cases/{}/judgements called", caseId);

		return judgementService.listByCase(caseId);
	}

	// DELETE /api/judgements/{judgementId}
	@DeleteMapping("/judgements/{judgementId}")
	public ResponseEntity<String> delete(@PathVariable Long judgementId) {

		log.info("DELETE /api/judgements/{} called", judgementId);

		judgementService.delete(judgementId);

		log.info("Judgement deleted successfully with id={}", judgementId);

		return ResponseEntity.ok("Judgement with ID " + judgementId + " deleted successfully");
	}
}