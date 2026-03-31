package com.justicegov.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.justicegov.demo.dto.*;
import com.justicegov.demo.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/profiles/documents")
public class DocumentController {
	private final DocumentService service;

	public DocumentController(DocumentService s) {
		this.service = s;
	}

	@PostMapping("/upload") // http://localhost:1234/profiles/documents/upload
	public String upload(@RequestBody DocumentUploadReq req) {
		return service.upload(req);
	}

	@GetMapping("/{role}/{id}") // http://localhost:1234/profiles/documents/{role}/{id}
	public List<DocumentResp> getDocs(@PathVariable String role, @PathVariable Long id) {
		return service.getByEntity(id, role);
	}

	@PatchMapping("/verify/{id}") // http://localhost:1234/profiles/documents/verify/{id}?status=VERIFIED
	public String verify(@PathVariable Long id, @RequestParam String status) {
		return service.verify(id, status);
	}
}