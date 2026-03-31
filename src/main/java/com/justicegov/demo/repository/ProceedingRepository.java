package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justicegov.demo.model.Proceeding;

@Repository
public interface ProceedingRepository extends JpaRepository<Proceeding, Long> {
    // Basic CRUD operations are inherited automatically
}