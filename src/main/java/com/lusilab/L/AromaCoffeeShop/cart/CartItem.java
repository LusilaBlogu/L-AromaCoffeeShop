package com.lusilab.L.AromaCoffeeShop.cart;

import com.lusilab.L.AromaCoffeeShop.entity.Coffee;
import com.lusilab.L.AromaCoffeeShop.entity.CoffeeSize;
import java.util.UUID;

public class CartItem {
    private String cartItemId;
    private Coffee coffee;
    private int quantity;
    private double priceAtPurchase;


    private CoffeeSize coffeeSize;
    private boolean extraSugar = false;
    private boolean extraChocolate = false;
    private boolean extraCinnamon = false;
    private boolean extraMilk = false;
    private boolean extraCookies = false;
    private boolean extraCaramel = false;
    private boolean extraVanillie = false;
    private boolean extraCacao = false;
    private boolean extraWhippedCream = false;


    public static final double PRICE_EXTRA_CHOCOLATE = 50.0;
    public static final double PRICE_EXTRA_CINNAMON = 20.0;
    public static final double PRICE_EXTRA_MILK = 30.0;
    public static final double PRICE_EXTRA_COOKIES = 70.0;
    public static final double PRICE_EXTRA_CARAMEL = 40.0;
    public static final double PRICE_EXTRA_VANILLIE = 30.0;
    public static final double PRICE_EXTRA_CACAO = 30.0;
    public static final double PRICE_EXTRA_WHIPPED_CREAM = 60.0;



    public CartItem() {
        this.cartItemId = UUID.randomUUID().toString();
    }


    public CartItem(Coffee coffee, int quantity, CoffeeSize size) {
        this();
        this.setCoffee(coffee);
        this.setQuantity(quantity);
        this.setCoffeeSize(size);
    }

    public String getCoffeeName() {
        return (this.coffee != null) ? this.coffee.getName() : "N/A";
    }

    public double getSubtotal() {
        if (this.quantity <= 0 || this.coffee == null) {
            return 0.0;
        }

        double currentItemBasePrice = this.priceAtPurchase;

        if (this.coffeeSize != null) {
            currentItemBasePrice *= this.coffeeSize.getPriceMultiplier();
        }

        double extrasTotal = 0.0;
        if (isExtraChocolate()) extrasTotal += PRICE_EXTRA_CHOCOLATE;
        if (isExtraCinnamon()) extrasTotal += PRICE_EXTRA_CINNAMON;
        if (isExtraMilk()) extrasTotal += PRICE_EXTRA_MILK;
        if (isExtraCookies()) extrasTotal += PRICE_EXTRA_COOKIES;
        if (isExtraCaramel()) extrasTotal += PRICE_EXTRA_CARAMEL;
        if (isExtraVanillie()) extrasTotal += PRICE_EXTRA_VANILLIE;
        if (isExtraCacao()) extrasTotal += PRICE_EXTRA_CACAO;
        if (isExtraWhippedCream()) extrasTotal += PRICE_EXTRA_WHIPPED_CREAM;


        return (currentItemBasePrice + extrasTotal) * this.quantity;
    }


    public String getCartItemId() {
        return cartItemId;
    }


    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
        if (this.coffee != null) {
            this.priceAtPurchase = this.coffee.getPrice();
        } else {
            this.priceAtPurchase = 0.0;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(1, quantity);
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }


    public CoffeeSize getCoffeeSize() {
        return coffeeSize;
    }

    public void setCoffeeSize(CoffeeSize coffeeSize) {
        this.coffeeSize = coffeeSize;
    }


    public boolean isExtraSugar() { return extraSugar; }
    public void setExtraSugar(boolean extraSugar) { this.extraSugar = extraSugar; }

    public boolean isExtraChocolate() { return extraChocolate; }
    public void setExtraChocolate(boolean extraChocolate) { this.extraChocolate = extraChocolate; }

    public boolean isExtraCinnamon() { return extraCinnamon; }
    public void setExtraCinnamon(boolean extraCinnamon) { this.extraCinnamon = extraCinnamon; }

    public boolean isExtraMilk() { return extraMilk; }
    public void setExtraMilk(boolean extraMilk) { this.extraMilk = extraMilk; }

    public boolean isExtraCookies() { return extraCookies; }
    public void setExtraCookies(boolean extraCookies) { this.extraCookies = extraCookies; }

    public boolean isExtraCaramel() { return extraCaramel; }
    public void setExtraCaramel(boolean extraCaramel) { this.extraCaramel = extraCaramel; }

    public boolean isExtraVanillie() { return extraVanillie; }
    public void setExtraVanillie(boolean extraVanillie) { this.extraVanillie = extraVanillie; }

    public boolean isExtraCacao() { return extraCacao; }
    public void setExtraCacao(boolean extraCacao) { this.extraCacao = extraCacao; }

    public boolean isExtraWhippedCream() { return extraWhippedCream; }
    public void setExtraWhippedCream(boolean extraWhippedCream) { this.extraWhippedCream = extraWhippedCream; }



    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId='" + cartItemId + '\'' +
                ", coffee=" + (coffee != null ? coffee.getName() : "null") +
                ", quantity=" + quantity +
                ", coffeeSize=" + coffeeSize +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}