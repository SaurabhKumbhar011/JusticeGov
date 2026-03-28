package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Citizen;
import com.example.demo.repository.CitizenRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/citizens")
@AllArgsConstructor
public class CitizenController {

	private CitizenRepository citizenRepository;

	@PostMapping // http://localhost:9866/api/citizens
	public ResponseEntity<Citizen> createCitizen(@RequestBody Citizen citizen) {
		Citizen saved = citizenRepository.save(citizen);
		return ResponseEntity.ok(saved);
	}

	@GetMapping("/{id}") // http://localhost:9866/api/citizens/1
	public ResponseEntity<Citizen> getCitizen(@PathVariable Long id) {
		return citizenRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}