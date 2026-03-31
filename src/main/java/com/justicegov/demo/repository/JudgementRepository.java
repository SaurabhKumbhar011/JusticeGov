package com.justicegov.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justicegov.demo.model.Judgement;

public interface JudgementRepository extends JpaRepository<Judgement, Long> {
	List<Judgement> findByCaseRef_Id(Long caseId);

}
