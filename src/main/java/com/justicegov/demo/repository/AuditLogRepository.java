package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justicegov.demo.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}