package com.catering.restaurant.repo;

import com.catering.restaurant.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> { }
