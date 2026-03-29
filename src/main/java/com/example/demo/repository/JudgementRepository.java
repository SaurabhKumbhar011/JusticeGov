package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Judgement;

public interface JudgementRepository extends JpaRepository<Judgement, Long> {
	List<Judgement> findByCaseRef_Id(Long caseId);

}
