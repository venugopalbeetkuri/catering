package com.catering.restaurant.dto;

import com.catering.restaurant.model.OrderStatus;
import java.time.Instant;
import java.util.List;

public class OrderDTO {
    public Long id;
    public Instant createdAt;
    public int etaMins;
    public double total;
    public OrderStatus status;
    public String note;
    public List<OrderItemDTO> items;

    public static class OrderItemDTO {
        public Long id;
        public Long menuItemId;
        public String name;
        public int qty;
        public double price;
        public int prepMins;
    }
}
