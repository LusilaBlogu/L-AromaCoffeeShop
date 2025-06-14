package com.lusilab.L.AromaCoffeeShop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "coffee")
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    private String origin;
    private String intensityOfCoffee;
    private String imageUrl;


    public Coffee() {
    }

    public Coffee(String name, String description, double price, String origin, String intensityOfCoffee, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.origin = origin;
        this.intensityOfCoffee = intensityOfCoffee;
        this.imageUrl = imageUrl;
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


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public String toString() {
        return "Coffee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}