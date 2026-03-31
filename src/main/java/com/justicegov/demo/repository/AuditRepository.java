package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justicegov.demo.model.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
}