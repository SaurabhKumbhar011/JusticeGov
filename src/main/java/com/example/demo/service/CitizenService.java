package com.example.demo.service;

import com.example.demo.dto.*;
import java.util.List;

public interface CitizenService {
    CitizenProfileResp register(CitizenProfileReq req);
    List<CitizenProfileResp> getAll();
    CitizenProfileResp getById(Long id);
    // Changed return type from String to CitizenProfileResp
    CitizenProfileResp updateProfile(Long id, ProfileUpdateReq req);
}