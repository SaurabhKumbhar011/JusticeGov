package com.justicegov.demo.service;

import com.justicegov.demo.dto.CourtOrderCreateRequest;
import com.justicegov.demo.dto.CourtOrderResponse;
import com.justicegov.demo.dto.CourtOrderUpdateRequest;

public interface CourtOrderService {

    CourtOrderResponse create(CourtOrderCreateRequest request);

    CourtOrderResponse getById(Long orderId);

    CourtOrderResponse update(Long orderId, CourtOrderUpdateRequest request);

	void delete(Long orderId);
}