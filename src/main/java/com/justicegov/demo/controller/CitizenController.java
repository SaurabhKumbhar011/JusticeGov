package com.justicegov.demo.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justicegov.demo.dto.CitizenProfileReq;
import com.justicegov.demo.dto.CitizenProfileResp;
import com.justicegov.demo.dto.ProfileUpdateReq;
import com.justicegov.demo.service.CitizenService;

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

}