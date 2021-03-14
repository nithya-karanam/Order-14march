package com.infosys.project.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.project.order.entity.OrderDetails;

public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {
	
}
