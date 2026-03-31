package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.GrantApplication;
import com.example.demo.model.ResearchProject;
import com.example.demo.model.enums.ApplicationStatus;
import com.example.demo.model.enums.ProjectStatus;
import com.example.demo.repository.GrantApplicationRepository;
import com.example.demo.repository.ResearchProjectRepository;

/**
 * Service Implementation for Module 4.6: Legal Research & Grant Management.
 */
@Service
public class ResearchServiceImpl implements ResearchServiceInterface {

    private static final Logger logger =
            LoggerFactory.getLogger(ResearchServiceImpl.class);

    @Autowired
    private ResearchProjectRepository projectRepository;

    @Autowired
    private GrantApplicationRepository grantRepository;

    /**
     * Creates a new Research Project.
     */
    @Override
    public ResearchProject createProject(ResearchProject project) {

        // ✅ Valid enum value
        project.setStatus(ProjectStatus.DRAFT);

        logger.info(
            "AUDIT: Initializing research project '{}'",
            project.getTitle()
        );

        return projectRepository.save(project);
    }

    /**
     * Submits a grant application for an existing research project.
     */
    @Override
    public GrantApplication applyForGrant(GrantApplication application) {

        Long projectId = application.getProject().getId();

        logger.info(
            "AUDIT: Processing grant application for Project ID: {}",
            projectId
        );

        ResearchProject project = projectRepository.findById(projectId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Research Project not found with ID: " + projectId
                )
            );

        // Ensure relationship consistency
        application.setProject(project);

        // Default date
        if (application.getSubmittedDate() == null) {
            application.setSubmittedDate(LocalDate.now());
        }

        // ✅ Valid enum
        application.setStatus(ApplicationStatus.SUBMITTED);

        // Maintain bidirectional consistency
        project.getApplications().add(application);

        logger.info(
            "AUDIT: Grant application submitted for Project ID {} by Researcher ID {}",
            projectId,
            application.getResearcher().getId()
        );

        return grantRepository.save(application);
    }

    /**
     * Updates grant application status.
     */
    @Override
    public GrantApplication updateGrantStatus(
            Long id, ApplicationStatus newStatus) {

        return grantRepository.findById(id)
            .map(application -> {
                application.setStatus(newStatus);
                logger.info(
                    "AUDIT: Grant Application ID {} updated to status {}",
                    id, newStatus
                );
                return grantRepository.save(application);
            })
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Grant Application not found with ID: " + id
                )
            );
    }

    /**
     * Retrieves all research projects.
     */
    @Override
    public List<ResearchProject> getAllProjects() {
        logger.info("AUDIT: Fetching all research projects");
        return projectRepository.findAll();
    }

    /**
     * Retrieves all grant applications.
     */
    @Override
    public List<GrantApplication> getAllGrantApplications() {
        logger.info("AUDIT: Fetching all grant applications");
        return grantRepository.findAll();
    }

	@Override
	public GrantApplication updateGrantStatus(Long id, String status) {
		// TODO Auto-generated method stub
		return null;
	}
}