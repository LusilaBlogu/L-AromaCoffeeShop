package com.lusilab.L.AromaCoffeeShop.repository;


import com.lusilab.L.AromaCoffeeShop.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

}
