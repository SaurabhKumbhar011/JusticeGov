package com.example.demo.service;

import com.example.demo.model.GrantApplication;
import com.example.demo.model.ResearchProject;
import com.example.demo.model.enums.ApplicationStatus;

import java.util.List;

/**
 * Service Interface for Module 4.6: Legal Research & Grant Management.
 * Defines the functional requirements for judicial research and funding.
 * @author JusticeGov Architect
 */
public interface ResearchServiceInterface {

    /**
     * Initializes a new research project.
     */
    ResearchProject createProject(ResearchProject project);

    /**
     * Submits a new grant application for a research initiative.
     */
    GrantApplication applyForGrant(GrantApplication application);

    /**
     * Updates the status (Approved/Rejected) of a grant application.
     */
    GrantApplication updateGrantStatus(Long id, String status);

    /**
     * Retrieves all research projects currently in the system.
     */
    List<ResearchProject> getAllProjects();

    /**
     * Retrieves all grant applications for administrative review.
     */
    List<GrantApplication> getAllGrantApplications();

	/**
	 * Updates grant application status.
	 */
	GrantApplication updateGrantStatus(Long id, ApplicationStatus newStatus);

	/**
	 * Updates the status of a grant application.
	 */
}