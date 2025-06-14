package com.lusilab.L.AromaCoffeeShop.entity; // Sigurohu që paketa është e saktë

public enum PaymentMethod {
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    PAYPAL("PayPal");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }

}