package com.example.demo.repository;

import com.example.demo.dto.JudgementResponse;
import com.example.demo.model.Judgement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JudgementRepository extends JpaRepository<Judgement, Long> {
    List<Judgement> findByCaseRef_Id(Long caseId);
	
}
