package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtOrderCreateRequest {
    private Long caseId;
    private Long judgeId;
    private String description;
    private LocalDate date;
    private OrderStatus status;
}