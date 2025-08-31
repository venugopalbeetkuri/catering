package com.catering.restaurant.repo;

import com.catering.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> { }
