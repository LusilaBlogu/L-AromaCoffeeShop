package com.lusilab.L.AromaCoffeeShop.entity;

public enum CoffeeSize {
    SMALL("Small", 1.0),
    MEDIUM("Medium", 1.25),
    LARGE("Large", 1.5);

    private final String displayName;
    private final double priceMultiplier;

    CoffeeSize(String displayName, double priceMultiplier) {
        this.displayName = displayName;
        this.priceMultiplier = priceMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    @Override
    public String toString() {
        return displayName;
    }
}