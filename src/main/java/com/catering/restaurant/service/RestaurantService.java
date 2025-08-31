package com.catering.restaurant.service;

import com.catering.restaurant.dto.RestaurantDTO;
import com.catering.restaurant.model.Restaurant;
import com.catering.restaurant.repo.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository repo;

    public RestaurantService(RestaurantRepository repo) { this.repo = repo; }

    public RestaurantDTO getRestaurant(long id) {
        Restaurant r = repo.findById(id).orElseGet(() -> {
            Restaurant x = new Restaurant();
            x.setId(id);
            x.setName("My Restaurant");
            return repo.save(x);
        });
        return toDto(r);
    }

    public RestaurantDTO update(RestaurantDTO d) {
        Restaurant r = repo.findById(1L).orElse(new Restaurant());
        r.setId(1L);
        r.setName(d.name);
        r.setTakeaway(d.takeaway);
        r.setDelivery(d.delivery);
        r.setOpen(d.open);
        r.setHoursJson(d.hoursJson == null ? "" : d.hoursJson);
        repo.save(r);
        return getRestaurant(1L);
    }

    public RestaurantDTO setOpen(long id, boolean open) {
        Restaurant r = repo.findById(id).orElseGet(() -> {
            Restaurant x = new Restaurant();
            x.setId(id);
            x.setName("My Restaurant");
            return x;
        });
        r.setOpen(open);
        repo.save(r);
        return toDto(r);
    }

    private RestaurantDTO toDto(Restaurant r) {
        RestaurantDTO d = new RestaurantDTO();
        d.name = r.getName();
        d.takeaway = r.isTakeaway();
        d.delivery = r.isDelivery();
        d.open = r.isOpen();
        d.hoursJson = r.getHoursJson();
        return d;
    }
}
