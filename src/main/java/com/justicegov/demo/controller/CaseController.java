
package com.justicegov.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.justicegov.demo.dto.CaseRequestDTO;
import com.justicegov.demo.dto.CaseResponseDTO;
import com.justicegov.demo.service.CaseService;

import java.util.List;

@RestController // Makes this class a REST API controller
@RequestMapping("/api/cases") // Base path for all endpoints
public class CaseController {

    //@Autowired // Injects the CaseService dependency
    private CaseService caseService;
    
    
    	@Autowired
        public CaseController(CaseService caseService) {
		super();
		this.caseService = caseService;
	}

		@PostMapping // /api/cases
        public ResponseEntity<CaseResponseDTO> fileCase(
            @RequestBody CaseRequestDTO request // Gets data from the request body
        ) {
        CaseResponseDTO response = caseService.fileCase(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping // /api/cases
    public ResponseEntity<List<CaseResponseDTO>> getAllCases() {
        return ResponseEntity.ok(caseService.getAllCases());
    }

        @GetMapping("/{id}") // /api/cases/{id}
        public ResponseEntity<CaseResponseDTO> getCaseById(
            @PathVariable Long id // Gets data from the URL path
        ) {
        return ResponseEntity.ok(caseService.getCaseById(id));
    }
}