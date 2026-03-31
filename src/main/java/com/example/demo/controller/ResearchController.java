package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Added for 201 status code
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.GrantApplication;
import com.example.demo.model.ResearchProject;
import com.example.demo.service.ResearchServiceInterface;

/**
 * REST Controller for Legal Research & Grant Management.
 */
@RestController
@RequestMapping("/api/research")
public class ResearchController {

    private static final Logger logger = LoggerFactory.getLogger(ResearchController.class);

    @Autowired
    private ResearchServiceInterface researchService;

    // --- PROJECT ENDPOINTS ---

    /**
     * Updated to return 201 Created
     */
    @PostMapping("/projects")
    public ResponseEntity<ResearchProject> createProject(@RequestBody ResearchProject project) {
        logger.info("REST: Creating project: {}", project.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(researchService.createProject(project));
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ResearchProject>> getAllProjects() {
        return ResponseEntity.ok(researchService.getAllProjects());
    }

    // --- GRANT ENDPOINTS ---

    /**
     * Updated to return 201 Created
     */
    @PostMapping("/grants/apply")
    public ResponseEntity<GrantApplication> applyForGrant(@RequestBody GrantApplication application) {
        logger.info("REST: Grant application for Project ID: {}", application.getProject());
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(researchService.applyForGrant(application));
    }

    /**
     * PUT is generally 200 OK because we are updating an existing application.
     */
    @PutMapping("/grants/{id}/status")
    public ResponseEntity<GrantApplication> updateGrantStatus(
            @PathVariable("id") Long id, 
            @RequestParam("status") String status) {
        
        logger.info("REST: Updating Grant ID {} to status: {}", id, status);
        return ResponseEntity.ok(researchService.updateGrantStatus(id, status));
    }

    /**
     * Helper endpoint to see all applications.
     */
    @GetMapping("/grants")
    public ResponseEntity<List<GrantApplication>> getAllGrants() {
        return ResponseEntity.ok(researchService.getAllGrantApplications());
    }
}