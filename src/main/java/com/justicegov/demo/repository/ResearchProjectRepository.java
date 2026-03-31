package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justicegov.demo.model.ResearchProject;

@Repository
public interface ResearchProjectRepository extends JpaRepository<ResearchProject, Long> {
}