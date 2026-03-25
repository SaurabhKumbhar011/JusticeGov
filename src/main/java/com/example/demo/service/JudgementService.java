package com.example.demo.service;

import com.example.demo.dto.JudgementCreateRequest;
import com.example.demo.dto.JudgementPatchRequest;
import com.example.demo.dto.JudgementResponse;
import com.example.demo.model.Judgement;

import java.util.List;

public interface JudgementService {
    JudgementResponse create(Long caseId, JudgementCreateRequest request);
    JudgementResponse getById(Long judgmentId);
    JudgementResponse patch(Long judgmentId, JudgementPatchRequest request);
    List<JudgementResponse> listByCase(Long caseId);
	void delete(Long judgementId);
	
}


