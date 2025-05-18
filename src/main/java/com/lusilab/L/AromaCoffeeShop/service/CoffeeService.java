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


    public Coffee updateCoffee(Long id, Coffee updatedCoffee) {
        Optional<Coffee> existingCoffee = coffeeRepository.findById(id);
        if (existingCoffee.isPresent()) {
            Coffee coffee = existingCoffee.get();
            coffee.setName(updatedCoffee.getName());
            coffee.setDescription(updatedCoffee.getDescription());
            coffee.setPrice(updatedCoffee.getPrice());
            coffee.setOrigin(updatedCoffee.getOrigin());
            coffee.setIntensityOfCoffee(updatedCoffee.getIntensityOfCoffee());
            return coffeeRepository.save(coffee);
        } else {
            return null;
        }
    }
    public void deleteCoffee(Long id) {
        coffeeRepository.deleteById(id);
    }
}
