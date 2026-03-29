package com.example.demo.repository;

import com.example.demo.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
    // Additional query methods can be defined here if needed
}