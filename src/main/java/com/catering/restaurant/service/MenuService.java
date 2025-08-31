package com.catering.restaurant.service;

import com.catering.restaurant.dto.MenuItemDTO;
import com.catering.restaurant.model.MenuItem;
import com.catering.restaurant.model.Restaurant;
import com.catering.restaurant.repo.MenuItemRepository;
import com.catering.restaurant.repo.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService {
    private final MenuItemRepository menuRepo;
    private final RestaurantRepository restRepo;

    public MenuService(MenuItemRepository menuRepo, RestaurantRepository restRepo) {
        this.menuRepo = menuRepo;
        this.restRepo = restRepo;
    }

    public List<MenuItemDTO> listAll() {
        return menuRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<MenuItemDTO> listActive() {
        return menuRepo.findByActiveTrue().stream().map(this::toDto).collect(Collectors.toList());
    }

    public MenuItemDTO create(MenuItemDTO dto) {
        MenuItem m = new MenuItem();
        apply(dto, m);
        Restaurant r = restRepo.findById(1L).orElseGet(() -> {
            Restaurant rr = new Restaurant();
            rr.setId(1L);
            rr.setName("My Restaurant");
            return restRepo.save(rr);
        });
        m.setRestaurant(r);
        return toDto(menuRepo.save(m));
    }

    public MenuItemDTO update(Long id, MenuItemDTO dto) {
        MenuItem m = menuRepo.findById(id).orElseThrow();
        apply(dto, m);
        return toDto(menuRepo.save(m));
    }

    public void delete(Long id) { menuRepo.deleteById(id); }

    private void apply(MenuItemDTO dto, MenuItem m) {
        m.setName(dto.name);
        m.setPrice(dto.price);
        m.setPrepMins(dto.prepMins);
        m.setActive(dto.active);
    }

    private MenuItemDTO toDto(MenuItem m) {
        MenuItemDTO d = new MenuItemDTO();
        d.id = m.getId();
        d.name = m.getName();
        d.price = m.getPrice();
        d.prepMins = m.getPrepMins();
        d.active = m.isActive();
        return d;
    }
}
