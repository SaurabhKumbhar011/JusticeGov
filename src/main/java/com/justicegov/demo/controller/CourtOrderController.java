package com.justicegov.demo.controller;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justicegov.demo.dto.CourtOrderCreateRequest;
import com.justicegov.demo.dto.CourtOrderResponse;
import com.justicegov.demo.dto.CourtOrderUpdateRequest;
import com.justicegov.demo.service.CourtOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourtOrderController {

	private final CourtOrderService courtOrderService;

	// POST /api/court-orders – create court order
	@PostMapping(path = "/court-orders", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CourtOrderResponse> create(@Valid @RequestBody CourtOrderCreateRequest request) {

		log.info("POST /api/court-orders called");

		CourtOrderResponse created = courtOrderService.create(request);

		log.info("Court order created successfully with id={}", created.getId());

		return ResponseEntity.created(URI.create("/api/court-orders/" + created.getId())).body(created);
	}

	// GET /api/court-orders/{orderId}
	@GetMapping("/court-orders/{orderId}")
	public CourtOrderResponse getById(@PathVariable Long orderId) {

		log.info("GET /api/court-orders/{} called", orderId);

		return courtOrderService.getById(orderId);
	}

	// PATCH /api/court-orders/{orderId}
	@PatchMapping(path = "/court-orders/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public CourtOrderResponse update(@PathVariable Long orderId, @RequestBody CourtOrderUpdateRequest request) {

		log.info("PATCH /api/court-orders/{} called", orderId);

		return courtOrderService.update(orderId, request);
	}

	// DELETE /api/court-orders/{orderId}
	@DeleteMapping("/court-orders/{orderId}")
	public ResponseEntity<String> delete(@PathVariable Long orderId) {

		log.info("DELETE /api/court-orders/{} called", orderId);

		courtOrderService.delete(orderId);

		log.info("Court order deleted successfully with id={}", orderId);

		return ResponseEntity.ok("Court order with ID " + orderId + " deleted successfully");
	}
}