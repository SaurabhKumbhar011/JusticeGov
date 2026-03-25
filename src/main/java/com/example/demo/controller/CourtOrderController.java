//package com.example.demo.controller;
//
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.demo.dto.CourtOrderCreateRequest;
//import com.example.demo.dto.CourtOrderUpdateRequest;
//import com.example.demo.dto.CourtOrderResponse;
//import com.example.demo.service.CourtOrderService;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(path = "/api/court-orders", produces = MediaType.APPLICATION_JSON_VALUE)
//public class CourtOrderController {
//
//	private final CourtOrderService courtOrderService;
//
//	//CREATE COURT ORDER
//	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<CourtOrderResponse> create(@Valid @RequestBody CourtOrderCreateRequest request) {
//
//		CourtOrderResponse created = courtOrderService.create(request);
//		return ResponseEntity.ok(created);
//	}
//
//	//GET COURT ORDER DETAILS
//	@GetMapping("/{orderId}")
//	public CourtOrderResponse getById(@PathVariable Long orderId) {
//		return courtOrderService.getById(orderId);
//	}
//
//	//UPDATE COURT ORDER
//	@PatchMapping(path = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public CourtOrderResponse update(@PathVariable Long orderId, @RequestBody CourtOrderUpdateRequest request) {
//
//		return courtOrderService.update(orderId, request);
//	}
//}

package com.example.demo.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.demo.dto.CourtOrderCreateRequest;
import com.example.demo.dto.CourtOrderResponse;
import com.example.demo.dto.CourtOrderUpdateRequest;
import com.example.demo.service.CourtOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourtOrderController {

	private static final Logger logger = LoggerFactory.getLogger(CourtOrderController.class);

	private final CourtOrderService courtOrderService;

	// POST /api/court-orders – create court order
	@PostMapping(path = "/court-orders", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CourtOrderResponse> create(@Valid @RequestBody CourtOrderCreateRequest request) {

		logger.info("POST /api/court-orders called");

		CourtOrderResponse created = courtOrderService.create(request);

		logger.info("Court order created successfully with id={}", created.getId());

		return ResponseEntity.created(URI.create("/api/court-orders/" + created.getId())).body(created);
	}

	// GET /api/court-orders/{orderId}
	@GetMapping("/court-orders/{orderId}")
	public CourtOrderResponse getById(@PathVariable Long orderId) {

		logger.info("GET /api/court-orders/{} called", orderId);

		return courtOrderService.getById(orderId);
	}

	// PATCH /api/court-orders/{orderId}
	@PatchMapping(path = "/court-orders/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public CourtOrderResponse update(@PathVariable Long orderId, @RequestBody CourtOrderUpdateRequest request) {

		logger.info("PATCH /api/court-orders/{} called", orderId);

		return courtOrderService.update(orderId, request);
	}

	// DELETE /api/court-orders/{orderId}
	@DeleteMapping("/court-orders/{orderId}")
	public ResponseEntity<Void> delete(@PathVariable Long orderId) {

		logger.info("DELETE /api/court-orders/{} called", orderId);

		courtOrderService.delete(orderId);

		logger.info("Court order deleted successfully with id={}", orderId);

		return ResponseEntity.noContent().build();
	}
}