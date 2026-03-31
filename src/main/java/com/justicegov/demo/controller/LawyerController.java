package com.justicegov.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.justicegov.demo.dto.*;
import com.justicegov.demo.service.LawyerService;

import java.util.List;

@RestController
@RequestMapping("/profiles/lawyers")
public class LawyerController {

	private final LawyerService lawyerService;

	public LawyerController(LawyerService lawyerService) {
		this.lawyerService = lawyerService;
	}

	@PostMapping // http://localhost:1234/profiles/lawyers
	public ResponseEntity<LawyerProfileResp> register(@RequestBody LawyerProfileReq req) {
		LawyerProfileResp response = lawyerService.register(req);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping // http://localhost:1234/profiles/lawyers
	public ResponseEntity<List<LawyerProfileResp>> getAll() {
		List<LawyerProfileResp> list = lawyerService.getAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}") // http://localhost:1234/profiles/lawyers/{id}
	public ResponseEntity<LawyerProfileResp> getById(@PathVariable Long id) {
		LawyerProfileResp response = lawyerService.getById(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}") // http://localhost:1234/profiles/lawyers/{id}
	public ResponseEntity<LawyerProfileResp> update(@PathVariable Long id, @RequestBody ProfileUpdateReq req) {
	    LawyerProfileResp response = lawyerService.updateProfile(id, req);
	    return ResponseEntity.ok(response);
	}
}