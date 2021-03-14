package com.infosys.project.order.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.infosys.project.order.entity.ProductsOrdered;
import com.infosys.project.order.entity.ProductsOrderedIdusingIdClass;

public interface ProductsOrderedRepo extends JpaRepository<ProductsOrdered, ProductsOrderedIdusingIdClass>{
List<ProductsOrdered> findByorderid(int orderid);
}
