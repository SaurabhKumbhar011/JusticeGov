package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justicegov.demo.model.ComplianceRecord;

@Repository
public interface ComplianceRepository extends JpaRepository<ComplianceRecord, Long> {
}