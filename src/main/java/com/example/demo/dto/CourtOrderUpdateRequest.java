package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.enums.OrderStatus;
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