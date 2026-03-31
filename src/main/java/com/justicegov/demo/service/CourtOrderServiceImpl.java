package com.justicegov.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justicegov.demo.dto.CourtOrderCreateRequest;
import com.justicegov.demo.dto.CourtOrderResponse;
import com.justicegov.demo.dto.CourtOrderUpdateRequest;
import com.justicegov.demo.exceptions.NotFoundException;
import com.justicegov.demo.model.Case;
import com.justicegov.demo.model.CourtOrder;
import com.justicegov.demo.model.User;
import com.justicegov.demo.model.enums.OrderStatus;
import com.justicegov.demo.repository.CourtOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CourtOrderServiceImpl implements CourtOrderService {

    private static final Logger logger =
            LoggerFactory.getLogger(CourtOrderServiceImpl.class);

    private final CourtOrderRepository courtOrderRepository;

    @Override
    public CourtOrderResponse create(CourtOrderCreateRequest request) {

        logger.info("Creating court order for caseId={} and judgeId={}",
                request.getCaseId(), request.getJudgeId());

        Case caseRef = new Case();
        caseRef.setId(request.getCaseId());

        User judge = new User();
        judge.setId(request.getJudgeId());

        CourtOrder order = new CourtOrder();
        order.setCaseRef(caseRef);
        order.setJudge(judge);
        order.setDescription(request.getDescription());
        order.setDate(request.getDate());
        order.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : OrderStatus.ISSUED
        );

        CourtOrder saved = courtOrderRepository.save(order);

        logger.info("Court order created successfully with id={}", saved.getId());

        return toResponse(saved);
    }

    @Override
    public CourtOrderResponse getById(Long orderId) {

        logger.info("Fetching court order with id={}", orderId);

        CourtOrder order = courtOrderRepository.findById(orderId)
                .orElseThrow(() -> {
                    logger.warn("Court order not found with id={}", orderId);
                    return new NotFoundException("Court Order not found: " + orderId);
                });

        logger.info("Court order fetched successfully with id={}", orderId);

        return toResponse(order);
    }

    @Override
    public CourtOrderResponse update(Long orderId, CourtOrderUpdateRequest request) {

        logger.info("Updating court order with id={}", orderId);

        CourtOrder order = courtOrderRepository.findById(orderId)
                .orElseThrow(() -> {
                    logger.warn("Cannot update. Court order not found with id={}", orderId);
                    return new NotFoundException("Court Order not found: " + orderId);
                });

        if (request.getDescription() != null) {
            order.setDescription(request.getDescription());
        }
        if (request.getDate() != null) {
            order.setDate(request.getDate());
        }
        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        CourtOrder updated = courtOrderRepository.save(order);

        logger.info("Court order updated successfully with id={}", updated.getId());

        return toResponse(updated);
    }

    @Override
    public void delete(Long orderId) {

        logger.info("Deleting court order with id={}", orderId);

        if (!courtOrderRepository.existsById(orderId)) {
            logger.warn("Cannot delete. Court order not found with id={}", orderId);
            throw new NotFoundException("Cannot delete. Court Order not found: " + orderId);
        }

        courtOrderRepository.deleteById(orderId);

        logger.info("Court order deleted successfully with id={}", orderId);
    }

    private CourtOrderResponse toResponse(CourtOrder o) {

        CourtOrderResponse response = new CourtOrderResponse();
        response.setId(o.getId());
        response.setCaseId(o.getCaseRef().getId());
        response.setJudgeId(o.getJudge().getId());
        //response.setJudgeName(o.getJudge().getName());
        response.setDescription(o.getDescription());
        response.setDate(o.getDate());
        response.setStatus(o.getStatus());

        return response;
    }
}