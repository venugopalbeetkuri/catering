package com.catering.restaurant.model;

import jakarta.persistence.*;

@Entity
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int prepMins;
    private boolean active = true;

    @ManyToOne
    private Restaurant restaurant;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getPrepMins() { return prepMins; }
    public void setPrepMins(int prepMins) { this.prepMins = prepMins; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
}
