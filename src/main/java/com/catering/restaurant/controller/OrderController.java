package com.catering.restaurant.controller;

import com.catering.restaurant.dto.OrderDTO;
import com.catering.restaurant.dto.OrderRequest;
import com.catering.restaurant.dto.StatusUpdateRequest;
import com.catering.restaurant.model.OrderStatus;
import com.catering.restaurant.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> list() { return ResponseEntity.ok(service.list()); }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable Long id) { return ResponseEntity.ok(service.get(id)); }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderRequest req) { return ResponseEntity.ok(service.create(req)); }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest r) {
        return ResponseEntity.ok(service.updateStatus(id, r.status));
    }

    // Convenience endpoints
    @PostMapping("/{id}/preparing")
    public ResponseEntity<OrderDTO> preparing(@PathVariable Long id) { return ResponseEntity.ok(service.updateStatus(id, OrderStatus.PREPARING)); }

    @PostMapping("/{id}/prepared")
    public ResponseEntity<OrderDTO> prepared(@PathVariable Long id) { return ResponseEntity.ok(service.updateStatus(id, OrderStatus.PREPARED)); }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderDTO> cancel(@PathVariable Long id) { return ResponseEntity.ok(service.cancel(id)); }
}
