package com.example.demo.controller;

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

}