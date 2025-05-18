package com.lusilab.L.AromaCoffeeShop.service;

import com.lusilab.L.AromaCoffeeShop.entity.Coffee;
import com.lusilab.L.AromaCoffeeShop.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }


    public Coffee getCoffeeById(Long id) {
        Optional<Coffee> coffee = coffeeRepository.findById(id);
        return coffee.orElse(null);
    }

    public Coffee saveCoffee(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    public void deleteCoffee(Long id) {
        coffeeRepository.deleteById(id);
    }
}
