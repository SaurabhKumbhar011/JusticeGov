package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justicegov.demo.model.LegalDocument;

import java.util.List;

public interface DocumentRepository extends JpaRepository<LegalDocument, Long> {
    List<LegalDocument> findByCitizenId(Long citizenId);
    List<LegalDocument> findByLawyerId(Long lawyerId);
}