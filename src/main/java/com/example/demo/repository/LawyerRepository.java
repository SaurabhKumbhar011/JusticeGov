package com.example.demo.repository;

import com.example.demo.model.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
	boolean existsByBarId(String barId);
}