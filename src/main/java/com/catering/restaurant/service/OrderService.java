package com.catering.restaurant.service;

import com.catering.restaurant.dto.OrderDTO;
import com.catering.restaurant.dto.OrderRequest;
import com.catering.restaurant.model.MenuItem;
import com.catering.restaurant.model.OrderEntity;
import com.catering.restaurant.model.OrderItem;
import com.catering.restaurant.model.OrderStatus;
import com.catering.restaurant.repo.MenuItemRepository;
import com.catering.restaurant.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepo;
    private final MenuItemRepository menuRepo;

    public OrderService(OrderRepository orderRepo, MenuItemRepository menuRepo) {
        this.orderRepo = orderRepo;
        this.menuRepo = menuRepo;
    }

    public List<OrderDTO> list() {
        return orderRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public OrderDTO get(Long id) { return toDto(orderRepo.findById(id).orElseThrow()); }

    public OrderDTO create(OrderRequest req) {
        OrderEntity o = new OrderEntity();
        double total = 0;
        int eta = 0;

        for (OrderRequest.Item it : req.items) {
            MenuItem menu = menuRepo.findById(it.menuItemId).orElseThrow();
            OrderItem oi = new OrderItem();
            oi.setMenuItemId(menu.getId());
            oi.setName(menu.getName());
            oi.setQty(it.qty);
            oi.setPrice(menu.getPrice());
            oi.setPrepMins(menu.getPrepMins());
            oi.setOrder(o);
            o.getItems().add(oi);

            total += menu.getPrice() * it.qty;
            eta = Math.max(eta, menu.getPrepMins() * it.qty);
        }
        o.setTotal(total);
        o.setEtaMins(eta);
        o.setStatus(OrderStatus.ACCEPTED);
        o.setNote(req.note);
        return toDto(orderRepo.save(o));
    }

    public OrderDTO updateStatus(Long id, OrderStatus status) {
        OrderEntity o = orderRepo.findById(id).orElseThrow();
        o.setStatus(status);
        return toDto(orderRepo.save(o));
    }

    public OrderDTO cancel(Long id) {
        OrderEntity o = orderRepo.findById(id).orElseThrow();
        // Basic guard: don't allow cancelling an already prepared order
        if (o.getStatus() == OrderStatus.PREPARED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot cancel an order that is already prepared");
        }
        o.setStatus(OrderStatus.CANCELLED);
        return toDto(orderRepo.save(o));
    }

    private OrderDTO toDto(OrderEntity o) {
        OrderDTO d = new OrderDTO();
        d.id = o.getId();
        d.createdAt = o.getCreatedAt();
        d.etaMins = o.getEtaMins();
        d.total = o.getTotal();
        d.status = o.getStatus();
        d.note = o.getNote();
        d.items = o.getItems().stream().map(oi -> {
            OrderDTO.OrderItemDTO x = new OrderDTO.OrderItemDTO();
            x.id = oi.getId();
            x.menuItemId = oi.getMenuItemId();
            x.name = oi.getName();
            x.qty = oi.getQty();
            x.price = oi.getPrice();
            x.prepMins = oi.getPrepMins();
            return x;
        }).collect(java.util.stream.Collectors.toList());
        return d;
    }
}
