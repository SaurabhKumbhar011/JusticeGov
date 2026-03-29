package com.example.demo.controller;

<<<<<<< HEAD
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
=======
import com.example.demo.dto.*;
import com.example.demo.service.CitizenService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/profiles/citizens")
public class CitizenController {
    private final CitizenService service;

    public CitizenController(CitizenService s) {
        this.service = s;
    }

    @PostMapping // http://localhost:1234/profiles/citizens 
    public CitizenProfileResp register(@RequestBody CitizenProfileReq req) {
        return service.register(req);
    }

    @GetMapping // http://localhost:1234/profiles/citizens
    public List<CitizenProfileResp> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}") // http://localhost:1234/profiles/citizens/{id}
    public CitizenProfileResp getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}") // Updated to return the full Object
    public CitizenProfileResp update(@PathVariable Long id, @RequestBody ProfileUpdateReq req) {
        return service.updateProfile(id, req);
    }
>>>>>>> origin/Vedasri
}