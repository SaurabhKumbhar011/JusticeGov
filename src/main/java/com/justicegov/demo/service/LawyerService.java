package com.justicegov.demo.service;

import java.util.List;

import com.justicegov.demo.dto.*;

public interface LawyerService {
    LawyerProfileResp register(LawyerProfileReq req);
    List<LawyerProfileResp> getAll();
    LawyerProfileResp getById(Long id);
    // Updated return type
    LawyerProfileResp updateProfile(Long id, ProfileUpdateReq req); 
}