package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.JudgementCreateRequest;
import com.example.demo.dto.JudgementPatchRequest;
import com.example.demo.dto.JudgementResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Case;
import com.example.demo.model.Judgement;
import com.example.demo.model.User;
import com.example.demo.model.enums.JudgementStatus;
import com.example.demo.repository.JudgementRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JudgementServiceImpl implements JudgementService {

	private final JudgementRepository judgementRepository;

	@Override
	public JudgementResponse create(Long caseId, JudgementCreateRequest request) {

		log.info("Creating judgement for caseId={} and judgeId={}", caseId, request.getJudgeId());

		Case caseRef = new Case();
		caseRef.setId(caseId);

		User judge = new User();
		judge.setId(request.getJudgeId());

		Judgement entity = new Judgement();
		entity.setCaseRef(caseRef);
		entity.setJudge(judge);
		entity.setSummary(request.getSummary());
		entity.setDate(request.getDate());
		entity.setStatus(request.getStatus() != null ? request.getStatus() : JudgementStatus.ISSUED);

		Judgement saved = judgementRepository.save(entity);

		log.info("Judgement created successfully with id={}", saved.getId());

		return toResponse(saved);
	}

	@Override
	public JudgementResponse getById(Long judgementId) {

		log.info("Fetching judgement with id={}", judgementId);

		Judgement entity = judgementRepository.findById(judgementId).orElseThrow(() -> {
			log.warn("Judgement not found with id={}", judgementId);
			return new NotFoundException("Judgement not found: " + judgementId);
		});

		return toResponse(entity);
	}

	@Override
	public JudgementResponse patch(Long judgementId, JudgementPatchRequest request) {

		log.info("Updating judgement with id={}", judgementId);

		Judgement entity = judgementRepository.findById(judgementId).orElseThrow(() -> {
			log.warn("Cannot update. Judgement not found with id={}", judgementId);
			return new NotFoundException("Judgement not found: " + judgementId);
		});

		if (request.getSummary() != null) {
			entity.setSummary(request.getSummary());
		}
		if (request.getDate() != null) {
			entity.setDate(request.getDate());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}

		Judgement updated = judgementRepository.save(entity);

		log.info("Judgement updated successfully with id={}", updated.getId());

		return toResponse(updated);
	}

	@Override
	public void delete(Long judgementId) {

		log.info("Deleting judgement with id={}", judgementId);

		if (!judgementRepository.existsById(judgementId)) {
			log.warn("Cannot delete. Judgement not found with id={}", judgementId);
			throw new NotFoundException("Cannot delete. Judgement not found: " + judgementId);
		}

		judgementRepository.deleteById(judgementId);

		log.info("Judgement deleted successfully with id={}", judgementId);
	}

	@Override
	public List<JudgementResponse> listByCase(Long caseId) {

		log.info("Listing judgements for caseId={}", caseId);

		List<Judgement> judgements = judgementRepository.findByCaseRef_Id(caseId);

		List<JudgementResponse> responseList = new ArrayList<>();
		for (Judgement j : judgements) {
			responseList.add(toResponse(j));
		}

		log.info("Found {} judgements for caseId={}", responseList.size(), caseId);

		return responseList;
	}

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