package com.justicegov.demo.service;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.justicegov.demo.dto.*;
import com.justicegov.demo.model.Lawyer;
import com.justicegov.demo.model.enums.GenericStatus;
import com.justicegov.demo.repository.LawyerRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LawyerServiceImpl implements LawyerService {

    private static final Logger log = LoggerFactory.getLogger(LawyerServiceImpl.class);
    private final LawyerRepository repository;



    @Override
    public LawyerProfileResp register(LawyerProfileReq req) {
        // Business Rule: Bar ID must be unique for Judiciary verification
        if (repository.existsByBarId(req.getBarId())) {
            throw new RuntimeException("Lawyer already exists with Bar ID: " + req.getBarId());
        }

        Lawyer lawyer = new Lawyer();
        lawyer.setName(req.getName());
        lawyer.setDob(req.getDob());
        lawyer.setBarId(req.getBarId());
        lawyer.setContactInfo(req.getContactInfo());
        
        lawyer.setStatus(GenericStatus.PENDING); 
        lawyer.setCreatedDate(LocalDateTime.now());
        lawyer.setLastModifiedDate(LocalDateTime.now());

        Lawyer saved = repository.save(lawyer);
        return mapToResp(saved);
    }

    @Override
    public LawyerProfileResp updateProfile(Long id, ProfileUpdateReq req) {
        Optional<Lawyer> opt = repository.findById(id);
        
        if (opt.isPresent()) {
            Lawyer l = opt.get();
            
            if (req.getContactInfo() != null) l.setContactInfo(req.getContactInfo());
            
            if (req.getStatus() != null) {
                try {
                    l.setStatus(GenericStatus.valueOf(req.getStatus().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    log.error("Invalid status provided: {}", req.getStatus());
                    // Throw exception so GlobalExceptionHandler catches it
                    throw new IllegalArgumentException("Invalid Status. Use: ACTIVE, INACTIVE, or PENDING_VERIFICATION");
                }
            }

            l.setLastModifiedDate(LocalDateTime.now()); 
            Lawyer saved = repository.save(l);
            return mapToResp(saved);
        } else {
            throw new RuntimeException("Lawyer not found with ID: " + id);
        }
    }

    @Override
    public List<LawyerProfileResp> getAll() {
        List<Lawyer> list = repository.findAll();
        List<LawyerProfileResp> resList = new ArrayList<>();
        
        // Standard loop (No Lambda)
        for (Lawyer l : list) {
            resList.add(mapToResp(l));
        }
        return resList;
    }

    @Override
    public LawyerProfileResp getById(Long id) {
        Optional<Lawyer> opt = repository.findById(id);
        
        // Changed from .map().orElseThrow() to traditional If-Else
        if (opt.isPresent()) {
            return mapToResp(opt.get());
        } else {
            throw new RuntimeException("Lawyer record not found for ID: " + id);
        }
    }

    private LawyerProfileResp mapToResp(Lawyer l) {
        LawyerProfileResp resp = new LawyerProfileResp();
        resp.setId(l.getId());
        resp.setName(l.getName());
        resp.setBarId(l.getBarId());
        resp.setContactInfo(l.getContactInfo());
        
        if (l.getStatus() != null) {
            resp.setStatus(l.getStatus().toString());
        }
        
        resp.setCreatedDate(l.getCreatedDate());
        resp.setLastModifiedDate(l.getLastModifiedDate());
        return resp;
    }
}