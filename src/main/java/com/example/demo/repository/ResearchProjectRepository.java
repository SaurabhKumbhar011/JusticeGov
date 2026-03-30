package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ResearchProject;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchProjectRepository extends JpaRepository<ResearchProject, Long> {
}