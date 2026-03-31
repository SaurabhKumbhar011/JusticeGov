package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justicegov.demo.model.Case;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
    // Additional query methods can be defined here if needed
}