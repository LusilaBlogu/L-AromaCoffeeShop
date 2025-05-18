package com.lusilab.L.AromaCoffeeShop.entity;
import jakarta.persistence.*;

@Entity
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String origin;
    private String intensityOfCoffee;

    public Coffee() {
    }


    public Coffee(String name, String description, double price, String origin, String intensityOfCoffee) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.origin = origin;
        this.intensityOfCoffee = intensityOfCoffee;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getIntensityOfCoffee() {
        return intensityOfCoffee;
    }

    public void setIntensityOfCoffee(String intensityOfCoffee) {
        this.intensityOfCoffee = intensityOfCoffee;
    }
}
