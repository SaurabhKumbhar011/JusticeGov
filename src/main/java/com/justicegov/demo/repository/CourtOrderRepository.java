package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justicegov.demo.model.CourtOrder;

public interface CourtOrderRepository extends JpaRepository<CourtOrder, Long> {
}