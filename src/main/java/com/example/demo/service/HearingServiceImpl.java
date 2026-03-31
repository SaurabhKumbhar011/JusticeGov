//package com.example.demo.service;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.exceptions.ResourceNotFoundException;
//import com.example.demo.model.Hearing;
//import com.example.demo.model.Proceeding;
//import com.example.demo.repository.HearingRepository;
//import com.example.demo.repository.ProceedingRepository;
//
///**
// * Service Implementation for Module 4.4: Hearing Scheduling & Court Proceedings.
// * Manages the lifecycle of judicial hearings and recorded notes.
// * @author JusticeGov Developer
// */f
//@Service
//public class HearingServiceImpl implements HearingServiceInterface {
//
//    private static final Logger logger = LoggerFactory.getLogger(HearingServiceImpl.class);
//
//    @Autowired
//    private HearingRepository hearingRepository;
//
//    @Autowired
//    private ProceedingRepository proceedingRepository;
//
//    /**
//     * Schedules a new hearing after validating judge availability.
//     * Prevents double-booking of judges for transparency.
//     */
//    @Override
//    public Hearing scheduleHearing(Hearing hearing) {
//        if (hearingRepository.existsByJudgeIdAndDateAndTime(
//                hearing.getJudgeId(), hearing.getDate(), hearing.getTime())) {
//            logger.error("AUDIT ERROR: Judge ID {} has a scheduling conflict on {} at {}", 
//                         hearing.getJudgeId(), hearing.getDate(), hearing.getTime());
//            throw new RuntimeException("Scheduling conflict: The selected Judge is already assigned to another hearing.");
//        }
//        
//        hearing.setStatus("Scheduled"); // Default status for new hearings
//        logger.info("AUDIT SUCCESS: Scheduled Hearing for Case ID: {}", hearing.getCaseId());
//        return hearingRepository.save(hearing);
//    }
//
//    /**
//     * Records judicial notes (proceedings) for a specific hearing.
//     * Resolves the 500 error by mapping the parent Hearing to the Proceeding.
//     */
//    @Override
//    public Proceeding saveProceeding(Long hearingId, Proceeding proceeding) {
//        // 1. Validate that the Hearing exists in the judiciary database
//        Hearing hearing = hearingRepository.findById(hearingId)
//                .orElseThrow(() -> new ResourceNotFoundException("Hearing not found with ID: " + hearingId));
//
//        // 2. Establish the JPA Relationship (Critical for preventing 500 errors)
//        proceeding.setHearing(hearing);
//        
//        // 3. Set a default date if not provided in the request
//        if (proceeding.getDate() == null) {
//            proceeding.setDate(java.time.LocalDate.now());
//        }
//
//        logger.info("AUDIT: Recorded new proceeding notes for Hearing ID: {}", hearingId);
//        return proceedingRepository.save(proceeding);
//    }
//
//    /**
//     * Retrieves all hearings associated with a specific case for historical review.
//     */
//    @Override
//    public List<Hearing> getCaseHistory(Long caseId) {
//        logger.info("AUDIT: Fetching judicial history for Case ID: {}", caseId);
//        return hearingRepository.findByCaseId(caseId);
//    }
//
//    /**
//     * Updates the status of a hearing (e.g., Completed, Adjourned).
//     */
//    @Override
//    public Hearing updateHearingStatus(Long id, String newStatus) {
//        return hearingRepository.findById(id)
//                .map(h -> {
//                    h.setStatus(newStatus);
//                    logger.info("AUDIT: Hearing ID {} status updated to {}", id, newStatus);
//                    return hearingRepository.save(h);
//                })
//                .orElseThrow(() -> new ResourceNotFoundException("Hearing not found with ID: " + id));
//    }
//}


package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Hearing;
import com.example.demo.model.Proceeding;
import com.example.demo.model.enums.HearingStatus;
import com.example.demo.model.enums.ProceedingStatus;
import com.example.demo.repository.HearingRepository;
import com.example.demo.repository.ProceedingRepository;

/**
 * Service Implementation for Module 4.4: Hearing Scheduling & Court Proceedings.
 * Manages the lifecycle of hearings and judicial proceedings.
 */
@Service
public class HearingServiceImpl implements HearingServiceInterface {

    private static final Logger logger =
            LoggerFactory.getLogger(HearingServiceImpl.class);

    @Autowired
    private HearingRepository hearingRepository;

    @Autowired
    private ProceedingRepository proceedingRepository;

    /**
     * Schedules a new hearing after validating judge availability.
     */
    @Override
    public Hearing scheduleHearing(Hearing hearing) {

        Long judgeId = hearing.getJudge().getId();
        LocalDateTime dateTime = hearing.getDateTime();

        boolean conflict =
                hearingRepository.existsByJudge_IdAndDateTime(
                        judgeId, dateTime);

        if (conflict) {
            logger.error(
                "AUDIT ERROR: Judge ID {} already has a hearing at {}",
                judgeId, dateTime
            );
            throw new RuntimeException(
                "Scheduling conflict: Judge is already assigned for this time slot."
            );
        }

        hearing.setStatus(HearingStatus.SCHEDULED);

        logger.info(
            "AUDIT SUCCESS: Hearing scheduled for Case ID: {}",
            hearing.getCaseRef().getId()
        );

        return hearingRepository.save(hearing);
    }

    /**
     * Records judicial proceedings for a specific hearing.
     */
    @Override
    public Proceeding saveProceeding(Long hearingId, Proceeding proceeding) {

        Hearing hearing = hearingRepository.findById(hearingId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Hearing not found with ID: " + hearingId));

        // Establish relationship
        proceeding.setHearing(hearing);

        // Default values
        if (proceeding.getDate() == null) {
            proceeding.setDate(LocalDate.now());
        }

        if (proceeding.getStatus() == null) {
            proceeding.setStatus(ProceedingStatus.IN_SESSION);
        }

        // Optional: Keep bidirectional consistency
        hearing.getProceedings().add(proceeding);

        logger.info(
            "AUDIT: Proceeding recorded for Hearing ID: {}",
            hearingId
        );

        return proceedingRepository.save(proceeding);
    }

    /**
     * Retrieves all hearings for a specific case.
     */
    @Override
    public List<Hearing> getCaseHistory(Long caseId) {
        logger.info(
            "AUDIT: Fetching hearing history for Case ID: {}",
            caseId
        );
        return hearingRepository.findByCaseRef_Id(caseId);
    }

    /**
     * Updates the status of a hearing.
     */
    @Override
    public Hearing updateHearingStatus(Long id, HearingStatus newStatus) {

        return hearingRepository.findById(id)
            .map(hearing -> {
                hearing.setStatus(newStatus);
                logger.info(
                    "AUDIT: Hearing ID {} status changed to {}",
                    id, newStatus
                );
                return hearingRepository.save(hearing);
            })
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Hearing not found with ID: " + id));
    }

	@Override
	public Hearing updateHearingStatus(Long id, String newStatus) {
		// TODO Auto-generated method stub
		return null;
	}
}
