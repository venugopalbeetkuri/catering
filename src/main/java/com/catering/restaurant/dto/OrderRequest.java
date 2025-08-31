package com.catering.restaurant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {
    @NotEmpty
    public List<Item> items;
    public String note;

    public static class Item {
        @NotNull
        public Long menuItemId;
        @Min(1)
        public int qty;
    }
}
