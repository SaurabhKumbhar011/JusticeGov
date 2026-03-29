package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtOrderResponse {
    private Long id;
    private Long caseId;
    private Long judgeId;
    private String description;
    private LocalDate date;
    private OrderStatus status;
}