package com.example.demo.service;

import com.example.demo.dto.CourtOrderCreateRequest;
import com.example.demo.dto.CourtOrderUpdateRequest;
import com.example.demo.dto.CourtOrderResponse;

public interface CourtOrderService {

    CourtOrderResponse create(CourtOrderCreateRequest request);

    CourtOrderResponse getById(Long orderId);

    CourtOrderResponse update(Long orderId, CourtOrderUpdateRequest request);

	void delete(Long orderId);
}