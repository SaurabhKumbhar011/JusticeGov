package com.example.demo.repository;

import com.example.demo.model.LegalDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<LegalDocument, Long> {
    List<LegalDocument> findByCitizenId(Long citizenId);
    List<LegalDocument> findByLawyerId(Long lawyerId);
}