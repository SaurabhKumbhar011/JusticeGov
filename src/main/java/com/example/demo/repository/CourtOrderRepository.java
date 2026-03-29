package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CourtOrder;

public interface CourtOrderRepository extends JpaRepository<CourtOrder, Long> {
}