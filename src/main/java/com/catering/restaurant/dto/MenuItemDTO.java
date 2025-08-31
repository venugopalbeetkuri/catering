package com.catering.restaurant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MenuItemDTO {
    public Long id;

    @NotBlank
    public String name;

    @Min(0)
    public double price;

    @NotNull @Min(0)
    public Integer prepMins;

    public boolean active = true;
}
