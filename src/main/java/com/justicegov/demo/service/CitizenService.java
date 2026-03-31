package com.justicegov.demo.service;

import java.util.List;

import com.justicegov.demo.dto.*;

public interface CitizenService {
    CitizenProfileResp register(CitizenProfileReq req);
    List<CitizenProfileResp> getAll();
    CitizenProfileResp getById(Long id);
    // Changed return type from String to CitizenProfileResp
    CitizenProfileResp updateProfile(Long id, ProfileUpdateReq req);
}