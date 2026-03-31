package com.justicegov.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justicegov.demo.dto.CourtOrderCreateRequest;
import com.justicegov.demo.dto.CourtOrderResponse;
import com.justicegov.demo.dto.CourtOrderUpdateRequest;
import com.justicegov.demo.exceptions.NotFoundException;
import com.justicegov.demo.model.Case;
import com.justicegov.demo.model.CourtOrder;
import com.justicegov.demo.model.User;
import com.justicegov.demo.model.enums.OrderStatus;
import com.justicegov.demo.repository.CourtOrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for managing Court Orders.
 * 
 * This class contains all business logic related to court orders such as
 * creation, retrieval, update, and deletion.
 */
@Slf4j // Enables logging
@Service // Marks this class as a Spring service component
@RequiredArgsConstructor // Enables constructor-based dependency injection
@Transactional // Ensures all DB operations run inside a transaction
public class CourtOrderServiceImpl implements CourtOrderService {

	// Repository for accessing CourtOrder data from the database
	private final CourtOrderRepository courtOrderRepository;

	/**
	 * Creates a new court order for a given case and judge.
	 */
	@Override
	public CourtOrderResponse create(CourtOrderCreateRequest request) {

		log.info("Creating court order for caseId={} and judgeId={}", request.getCaseId(), request.getJudgeId());

		// Create a lightweight Case reference using only the ID
		// (avoids unnecessary database fetch)
		Case caseRef = new Case();
		caseRef.setId(request.getCaseId());

		// Create a lightweight Judge reference using only the ID
		User judge = new User();
		judge.setId(request.getJudgeId());

		// Create CourtOrder entity from request data
		CourtOrder order = new CourtOrder();
		order.setCaseRef(caseRef);
		order.setJudge(judge);
		order.setDescription(request.getDescription());
		order.setDate(request.getDate());

		// Set default status if not provided by client
		order.setStatus(request.getStatus() != null ? request.getStatus() : OrderStatus.ISSUED);

		// Persist court order into database
		CourtOrder saved = courtOrderRepository.save(order);

		log.info("Court order created successfully with id={}", saved.getId());

		// Convert entity to response DTO
		return toResponse(saved);
	}

	/**
	 * Fetches a court order by its ID.
	 *
	 * Throws NotFoundException if the court order does not exist.
	 */
	@Override
	public CourtOrderResponse getById(Long orderId) {

		log.info("Fetching court order with id={}", orderId);

		CourtOrder order = courtOrderRepository.findById(orderId).orElseThrow(() -> {
			log.warn("Court order not found with id={}", orderId);
			return new NotFoundException("Court Order not found: " + orderId);
		});

		log.info("Court order fetched successfully with id={}", orderId);

		return toResponse(order);
	}

	/**
	 * Updates an existing court order.
	 *
	 * Only fields provided in the request are updated (PATCH behavior).
	 */
	@Override
	public CourtOrderResponse update(Long orderId, CourtOrderUpdateRequest request) {

		log.info("Updating court order with id={}", orderId);

		// Fetch existing court order or throw error if not found
		CourtOrder order = courtOrderRepository.findById(orderId).orElseThrow(() -> {
			log.warn("Cannot update. Court order not found with id={}", orderId);
			return new NotFoundException("Court Order not found: " + orderId);
		});

		// Update fields only if they are present in request
		if (request.getDescription() != null) {
			order.setDescription(request.getDescription());
		}
		if (request.getDate() != null) {
			order.setDate(request.getDate());
		}
		if (request.getStatus() != null) {
			order.setStatus(request.getStatus());
		}

		// Save updated court order
		CourtOrder updated = courtOrderRepository.save(order);

		log.info("Court order updated successfully with id={}", updated.getId());

		return toResponse(updated);
	}

	/**
	 * Deletes a court order by its ID.
	 *
	 * Throws NotFoundException if the court order does not exist.
	 */
	@Override
	public void delete(Long orderId) {

		log.info("Deleting court order with id={}", orderId);

		// Check existence before deletion to provide meaningful error response
		if (!courtOrderRepository.existsById(orderId)) {
			log.warn("Cannot delete. Court order not found with id={}", orderId);
			throw new NotFoundException("Cannot delete. Court Order not found: " + orderId);
		}

		// Perform delete operation
		courtOrderRepository.deleteById(orderId);

		log.info("Court order deleted successfully with id={}", orderId);
	}

	/**
	 * Converts CourtOrder entity to CourtOrderResponse DTO.
	 *
	 * This prevents exposing entity objects directly to API clients.
	 */
	private CourtOrderResponse toResponse(CourtOrder o) {

		CourtOrderResponse response = new CourtOrderResponse();
		response.setId(o.getId());
		response.setCaseId(o.getCaseRef().getId());
		response.setJudgeId(o.getJudge().getId());
		response.setDescription(o.getDescription());
		response.setDate(o.getDate());
		response.setStatus(o.getStatus());

		return response;
	}
}