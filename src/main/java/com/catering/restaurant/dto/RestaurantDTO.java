package com.catering.restaurant.dto;

public class RestaurantDTO {
    public String name;
    public boolean takeaway;
    public boolean delivery;
    public boolean open;      // current open/close status
    public String hoursJson;  // pass-through JSON string for per-day hours
}
