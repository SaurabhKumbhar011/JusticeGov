package com.justicegov.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justicegov.demo.dto.JudgementCreateRequest;
import com.justicegov.demo.dto.JudgementPatchRequest;
import com.justicegov.demo.dto.JudgementResponse;
import com.justicegov.demo.exceptions.NotFoundException;
import com.justicegov.demo.model.Case;
import com.justicegov.demo.model.Judgement;
import com.justicegov.demo.model.User;
import com.justicegov.demo.model.enums.JudgementStatus;
import com.justicegov.demo.repository.JudgementRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Judgement management.
 *
 * This class contains all business logic related to judgements such as
 * creation, retrieval, partial update, deletion, and listing by case.
 */
@Slf4j // Enables SLF4J logging
@Service // Marks this class as a Spring service component
@RequiredArgsConstructor // Enables constructor-based dependency injection
@Transactional // Ensures DB operations are executed within a transaction
public class JudgementServiceImpl implements JudgementService {

	// Repository used for database operations on Judgement entity
	private final JudgementRepository judgementRepository;

	/**
	 * Creates a new judgement for a given case.
	 */
	@Override
	public JudgementResponse create(Long caseId, JudgementCreateRequest request) {

		log.info("Creating judgement for caseId={} and judgeId={}", caseId, request.getJudgeId());

		// Create lightweight Case reference using only ID
		// (avoids unnecessary database fetch)
		Case caseRef = new Case();
		caseRef.setId(caseId);

		// Create lightweight Judge reference using only ID
		User judge = new User();
		judge.setId(request.getJudgeId());

		// Create Judgement entity and populate fields from request DTO
		Judgement entity = new Judgement();
		entity.setCaseRef(caseRef);
		entity.setJudge(judge);
		entity.setSummary(request.getSummary());
		entity.setDate(request.getDate());

		// Set status from request or default to ISSUED
		entity.setStatus(request.getStatus() != null ? request.getStatus() : JudgementStatus.ISSUED);

		// Persist judgement in database
		Judgement saved = judgementRepository.save(entity);

		log.info("Judgement created successfully with id={}", saved.getId());

		// Convert entity to response DTO
		return toResponse(saved);
	}

	/**
	 * Fetches a judgement by its ID.
	 *
	 * Throws NotFoundException if judgement does not exist.
	 */
	@Override
	public JudgementResponse getById(Long judgementId) {

		log.info("Fetching judgement with id={}", judgementId);

		// Fetch judgement or throw 404 if not found
		Judgement entity = judgementRepository.findById(judgementId).orElseThrow(() -> {
			log.warn("Judgement not found with id={}", judgementId);
			return new NotFoundException("Judgement not found: " + judgementId);
		});

		return toResponse(entity);
	}

	/**
	 * Partially updates an existing judgement.
	 *
	 * Only fields provided in the PATCH request are updated.
	 */
	@Override
	public JudgementResponse patch(Long judgementId, JudgementPatchRequest request) {

		log.info("Updating judgement with id={}", judgementId);

		// Fetch existing judgement or throw error if not found
		Judgement entity = judgementRepository.findById(judgementId).orElseThrow(() -> {
			log.warn("Cannot update. Judgement not found with id={}", judgementId);
			return new NotFoundException("Judgement not found: " + judgementId);
		});

		// Update fields only if they are present in request
		if (request.getSummary() != null) {
			entity.setSummary(request.getSummary());
		}
		if (request.getDate() != null) {
			entity.setDate(request.getDate());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}

		// Save updated judgement
		Judgement updated = judgementRepository.save(entity);

		log.info("Judgement updated successfully with id={}", updated.getId());

		return toResponse(updated);
	}

	/**
	 * Deletes a judgement by its ID.
	 *
	 * Throws NotFoundException if judgement does not exist.
	 */
	@Override
	public void delete(Long judgementId) {

		log.info("Deleting judgement with id={}", judgementId);

		// Check existence before deletion to avoid silent failure
		if (!judgementRepository.existsById(judgementId)) {
			log.warn("Cannot delete. Judgement not found with id={}", judgementId);
			throw new NotFoundException("Cannot delete. Judgement not found: " + judgementId);
		}

		// Delete judgement from database
		judgementRepository.deleteById(judgementId);

		log.info("Judgement deleted successfully with id={}", judgementId);
	}

	/**
	 * Lists all judgements for a given case.
	 */
	@Override
	public List<JudgementResponse> listByCase(Long caseId) {

		log.info("Listing judgements for caseId={}", caseId);

		// Fetch all judgements belonging to the given case
		List<Judgement> judgements = judgementRepository.findByCaseRef_Id(caseId);

		// Convert entity list to response DTO list
		List<JudgementResponse> responseList = new ArrayList<>();
		for (Judgement j : judgements) {
			responseList.add(toResponse(j));
		}

		log.info("Found {} judgements for caseId={}", responseList.size(), caseId);

		return responseList;
	}

	/**
	 * Converts Judgement entity to JudgementResponse DTO.
	 *
	 * This prevents exposing entity objects directly to API clients.
	 */
	private JudgementResponse toResponse(Judgement j) {

		JudgementResponse response = new JudgementResponse();
		response.setId(j.getId());
		response.setCaseId(j.getCaseRef().getId());
		response.setJudgeId(j.getJudge().getId());
		response.setSummary(j.getSummary());
		response.setDate(j.getDate());
		response.setStatus(j.getStatus());

		return response;
	}
}