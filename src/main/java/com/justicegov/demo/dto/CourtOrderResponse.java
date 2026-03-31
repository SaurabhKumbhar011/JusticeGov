package com.justicegov.demo.dto;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtOrderResponse {
    private Long id;
    private Long caseId;
    private Long judgeId;
    private String description;
    private LocalDate date;
    private OrderStatus status;
}