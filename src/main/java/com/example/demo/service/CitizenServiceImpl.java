package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.Citizen;
import com.example.demo.model.enums.GenericStatus;
import com.example.demo.repository.CitizenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CitizenServiceImpl implements CitizenService {

    private static final Logger log = LoggerFactory.getLogger(CitizenServiceImpl.class);
    private final CitizenRepository repo;

    public CitizenServiceImpl(CitizenRepository repo) {
        this.repo = repo;
    }

    @Override
    public CitizenProfileResp register(CitizenProfileReq req) {
        log.info("Registering Citizen: {}", req.getName());
        
        Citizen c = new Citizen();
        c.setName(req.getName());
        c.setDob(req.getDob());
        c.setGender(req.getGender());
        c.setAddress(req.getAddress());
        c.setContactInfo(req.getContactInfo());
        
        // Initial status for judiciary validation
        c.setStatus(GenericStatus.PENDING); 
        
        c.setCreatedDate(LocalDateTime.now());
        c.setLastModifiedDate(LocalDateTime.now());

        Citizen saved = repo.save(c);
        return mapToResp(saved);
    }

    @Override
    public CitizenProfileResp updateProfile(Long id, ProfileUpdateReq req) {
        log.info("Updating profile for Citizen ID: {}", id);
        
        Optional<Citizen> opt = repo.findById(id);

        if (opt.isPresent()) {
            Citizen c = opt.get();
            
            // Selective updates
            if (req.getAddress() != null) {
                c.setAddress(req.getAddress());
            }
            if (req.getContactInfo() != null) {
                c.setContactInfo(req.getContactInfo());
            }
            
            // Status transition logic
            if (req.getStatus() != null) {
                try {
                    c.setStatus(GenericStatus.valueOf(req.getStatus().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    log.error("Invalid status provided: {}", req.getStatus());
                    // Throwing exception for the GlobalExceptionHandler to catch
                    throw new IllegalArgumentException("Invalid Status. Use: ACTIVE, INACTIVE, or PENDING_VERIFICATION");
                }
            }

            // Mandatory audit update
            c.setLastModifiedDate(LocalDateTime.now()); 
            
            Citizen saved = repo.save(c);
            log.info("Profile updated for ID: {}", id);
            return mapToResp(saved); 
        } else {
            throw new RuntimeException("Citizen not found with ID: " + id);
        }
    }

    @Override
    public List<CitizenProfileResp> getAll() {
        List<Citizen> list = repo.findAll();
        List<CitizenProfileResp> responseList = new ArrayList<>();
        
        // Standard for-each loop instead of .stream().map().collect()
        for (Citizen citizen : list) {
            CitizenProfileResp resp = mapToResp(citizen);
            responseList.add(resp);
        }
        
        return responseList;
    }

    @Override
    public CitizenProfileResp getById(Long id) {
        Optional<Citizen> opt = repo.findById(id);
        
        // Traditional if-else instead of .orElseThrow()
        if (opt.isPresent()) {
            return mapToResp(opt.get());
        } else {
            throw new RuntimeException("Citizen not found with ID: " + id);
        }
    }

    private CitizenProfileResp mapToResp(Citizen c) {
        CitizenProfileResp resp = new CitizenProfileResp();
        resp.setId(c.getId());
        resp.setName(c.getName());
        resp.setAddress(c.getAddress());
        resp.setContactInfo(c.getContactInfo());
        
        if (c.getStatus() != null) {
            resp.setStatus(c.getStatus().toString());
        }
        
        resp.setCreatedDate(c.getCreatedDate());
        resp.setLastModifiedDate(c.getLastModifiedDate()); 
        return resp;
    }
}