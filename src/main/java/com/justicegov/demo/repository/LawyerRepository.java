package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justicegov.demo.model.Lawyer;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
}
