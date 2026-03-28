package com.example.demo.service;

import com.example.demo.dto.*;
import java.util.List;

public interface LawyerService {
    LawyerProfileResp register(LawyerProfileReq req);
    List<LawyerProfileResp> getAll();
    LawyerProfileResp getById(Long id);
    // Updated return type
    LawyerProfileResp updateProfile(Long id, ProfileUpdateReq req); 
}