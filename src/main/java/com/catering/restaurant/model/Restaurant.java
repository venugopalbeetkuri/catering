package com.catering.restaurant.model;

import jakarta.persistence.*;

@Entity
public class Restaurant {
    @Id
    private Long id = 1L;

    private String name;
    private boolean takeaway = true;
    private boolean delivery = true;
    private boolean open = true;

    // JSON string for hours map (mon..sun -> open/close). Keep simple for demo.
    @Column(length = 4000)
    private String hoursJson = "";

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isTakeaway() { return takeaway; }
    public void setTakeaway(boolean takeaway) { this.takeaway = takeaway; }

    public boolean isDelivery() { return delivery; }
    public void setDelivery(boolean delivery) { this.delivery = delivery; }

    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }

    public String getHoursJson() { return hoursJson; }
    public void setHoursJson(String hoursJson) { this.hoursJson = hoursJson; }
}
