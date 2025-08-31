package com.catering.restaurant.dto;

import com.catering.restaurant.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateRequest {
    @NotNull
    public OrderStatus status;
}
