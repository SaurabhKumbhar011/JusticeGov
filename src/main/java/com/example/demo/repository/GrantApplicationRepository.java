package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.GrantApplication;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantApplicationRepository extends JpaRepository<GrantApplication, Long> {
}