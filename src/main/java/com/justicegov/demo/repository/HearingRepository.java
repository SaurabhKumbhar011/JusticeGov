package com.justicegov.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justicegov.demo.model.Hearing;

import java.time.LocalDateTime;
import java.util.List;

public interface HearingRepository extends JpaRepository<Hearing, Long> {

    // 1. REMOVE or COMMENT OUT this line (it's causing the crash):
    // List<Hearing> findByCaseId(Long caseId); 

    // 2. KEEP this line (this is the correct one for your entity):
    List<Hearing> findByCaseRef_Id(Long caseId);

    // 3. KEEP this line (your scheduling conflict fix):
    boolean existsByJudge_IdAndDateTime(Long judgeId, LocalDateTime dateTime);
}