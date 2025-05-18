package com.lusilab.L.AromaCoffeeShop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class CoffeeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;
    private CoffeeSize coffeeSize;
    private int quantity;
    private PaymentMethod paymentMethod;
    private Boolean extraSugar;
    private Boolean extraChocolate;
    private Boolean extraCinnamon;
    private Boolean extraMilk;
    private Boolean extraCookies;
    private Boolean extraCaramel;
    private Boolean extraVanillie;
    private Boolean extraCacao;
    private Boolean extraWhippedCream;

    public CoffeeOrder() {
    }

    public CoffeeOrder(Coffee coffee, CoffeeSize coffeeSize, int quantity, PaymentMethod paymentMethod,
                       Boolean extraSugar, Boolean extraChocolate, Boolean extraCinnamon, Boolean extraMilk, Boolean extraVanillie, Boolean extraCookies, Boolean extraCaramel, Boolean extraCacao, Boolean extraWhippedCream) {
        this.coffee = coffee;
        this.coffeeSize = coffeeSize;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.extraSugar = extraSugar;
        this.extraChocolate = extraChocolate;
        this.extraCinnamon = extraCinnamon;
        this.extraMilk = extraMilk;
        this.extraVanillie = extraVanillie;
        this.extraCookies = extraCookies;
        this.extraCaramel = extraCaramel;
        this.extraCacao = extraCacao;
        this.extraWhippedCream = extraWhippedCream;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public CoffeeSize getCoffeeSize() {
        return coffeeSize;
    }

    public void setCoffeeSize(CoffeeSize coffeeSize) {
        this.coffeeSize = coffeeSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getExtraSugar() {
        return extraSugar;
    }

    public void setExtraSugar(Boolean extraSugar) {
        this.extraSugar = extraSugar;
    }

    public Boolean getExtraChocolate() {
        return extraChocolate;
    }

    public void setExtraChocolate(Boolean extraChocolate) {
        this.extraChocolate = extraChocolate;
    }

    public Boolean getExtraCinnamon() {
        return extraCinnamon;
    }

    public void setExtraCinnamon(Boolean extraCinnamon) {
        this.extraCinnamon = extraCinnamon;
    }

    public Boolean getExtraMilk() {
        return extraMilk;
    }

    public void setExtraMilk(Boolean extraMilk) {
        this.extraMilk = extraMilk;
    }

    public Boolean getExtraCookies() {
        return extraCookies;
    }

    public void setExtraCookies(Boolean extraCookies) {
        this.extraCookies = extraCookies;
    }

    public Boolean getExtraCaramel() {
        return extraCaramel;
    }

    public void setExtraCaramel(Boolean extraCaramel) {
        this.extraCaramel = extraCaramel;
    }

    public Boolean getExtraVanillie() {
        return extraVanillie;
    }

    public void setExtraVanillie(Boolean extraVanillie) {
        this.extraVanillie = extraVanillie;
    }

    public Boolean getExtraCacao() {
        return extraCacao;
    }

    public void setExtraCacao(Boolean extraCacao) {
        this.extraCacao = extraCacao;
    }

    public Boolean getExtraWhippedCream() {
        return extraWhippedCream;
    }

    public void setExtraWhippedCream(Boolean extraWhippedCream) {
        this.extraWhippedCream = extraWhippedCream;
    }
}
