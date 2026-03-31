package com.justicegov.demo.dto;

import java.time.LocalDate;

import com.justicegov.demo.model.enums.OrderStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtOrderUpdateRequest {
    private String description;
    private LocalDate date;
    private OrderStatus status;
}