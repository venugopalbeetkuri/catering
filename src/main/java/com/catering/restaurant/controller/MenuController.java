package com.catering.restaurant.controller;

import com.catering.restaurant.dto.MenuItemDTO;
import com.catering.restaurant.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService service;
    public MenuController(MenuService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> all() { return ResponseEntity.ok(service.listAll()); }

    @GetMapping("/active")
    public ResponseEntity<List<MenuItemDTO>> active() { return ResponseEntity.ok(service.listActive()); }

    @PostMapping
    public ResponseEntity<MenuItemDTO> create(@Valid @RequestBody MenuItemDTO dto) { return ResponseEntity.ok(service.create(dto)); }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDTO> update(@PathVariable Long id, @Valid @RequestBody MenuItemDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
