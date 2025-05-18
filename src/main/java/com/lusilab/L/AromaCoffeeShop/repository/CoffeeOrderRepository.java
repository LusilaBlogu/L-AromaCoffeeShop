package com.lusilab.L.AromaCoffeeShop.repository;

import com.lusilab.L.AromaCoffeeShop.entity.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
