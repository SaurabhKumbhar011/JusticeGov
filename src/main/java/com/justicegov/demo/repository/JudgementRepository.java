package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justicegov.demo.dto.JudgementResponse;
import com.justicegov.demo.model.Judgement;

import java.util.List;

public interface JudgementRepository extends JpaRepository<Judgement, Long> {
    List<Judgement> findByCaseRef_Id(Long caseId);
	
}
