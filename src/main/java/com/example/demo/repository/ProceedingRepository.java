package com.example.demo.repository;

import com.example.demo.model.Proceeding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProceedingRepository extends JpaRepository<Proceeding, Long> {
    // Basic CRUD operations are inherited automatically
}