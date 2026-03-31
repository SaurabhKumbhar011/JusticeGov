package com.justicegov.demo.service;

import java.util.List;

import com.justicegov.demo.dto.JudgementCreateRequest;
import com.justicegov.demo.dto.JudgementPatchRequest;
import com.justicegov.demo.dto.JudgementResponse;
import com.justicegov.demo.model.Judgement;

public interface JudgementService {
    JudgementResponse create(Long caseId, JudgementCreateRequest request);
    JudgementResponse getById(Long judgmentId);
    JudgementResponse patch(Long judgmentId, JudgementPatchRequest request);
    List<JudgementResponse> listByCase(Long caseId);
	void delete(Long judgementId);
	
}


