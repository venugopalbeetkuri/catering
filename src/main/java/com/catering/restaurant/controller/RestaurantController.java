package com.catering.restaurant.controller;

import com.catering.restaurant.dto.RestaurantDTO;
import com.catering.restaurant.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final RestaurantService service;

    public RestaurantController(RestaurantService service) { this.service = service; }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> get(@PathVariable Long id) { return ResponseEntity.ok(service.getRestaurant(id)); }

    @PutMapping
    public ResponseEntity<RestaurantDTO> update(@RequestBody RestaurantDTO d) { return ResponseEntity.ok(service.update(d)); }

    @PostMapping("/{id}/open")
    public ResponseEntity<RestaurantDTO> open(@PathVariable Long id) {
        return ResponseEntity.ok(service.setOpen(id, true));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<RestaurantDTO> close(@PathVariable Long id) {
        return ResponseEntity.ok(service.setOpen(id, false));
    }
}
