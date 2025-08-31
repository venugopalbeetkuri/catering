package com.catering.restaurant.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long menuItemId;
    private String name;
    private int qty;
    private double price;
    private int prepMins;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMenuItemId() { return menuItemId; }
    public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getPrepMins() { return prepMins; }
    public void setPrepMins(int prepMins) { this.prepMins = prepMins; }

    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }
}
