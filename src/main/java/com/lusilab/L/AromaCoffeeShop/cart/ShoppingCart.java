package com.lusilab.L.AromaCoffeeShop.cart;

import com.lusilab.L.AromaCoffeeShop.entity.PaymentMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingCart {

    private List<CartItem> items;
    private double totalAmount;
    private PaymentMethod paymentMethod;

    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;

    }


    public void addItem(CartItem newItem) {
        if (newItem == null || newItem.getCoffee() == null) {
            System.err.println("ShoppingCart: Attempted to add a null or invalid CartItem.");
            return;
        }
        items.add(newItem);
        calculateTotal();
        System.out.println("ShoppingCart: Added item " + newItem.getCoffeeName() + ". New total: " + this.totalAmount);
    }


    public boolean removeItem(String cartItemId) {
        if (cartItemId == null || this.items.isEmpty()) {
            return false;
        }

        boolean removed = items.removeIf(item -> Objects.equals(item.getCartItemId(), cartItemId));

        if (removed) {
            calculateTotal();
            System.out.println("ShoppingCart: Item with ID " + cartItemId + " removed. New total: " + this.totalAmount);
        } else {
            System.out.println("ShoppingCart: Item with ID " + cartItemId + " not found for removal.");
        }
        return removed;
    }


    public boolean updateItemQuantity(String cartItemId, int newQuantity) {
        if (cartItemId == null) return false;

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (Objects.equals(item.getCartItemId(), cartItemId)) {
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                    System.out.println("ShoppingCart: Quantity updated for item ID " + cartItemId + " to " + newQuantity);
                } else {

                    items.remove(i);
                    System.out.println("ShoppingCart: Item ID " + cartItemId + " removed due to quantity <= 0.");
                }
                calculateTotal();
                return true;
            }
        }
        System.out.println("ShoppingCart: Item ID " + cartItemId + " not found for quantity update.");
        return false;
    }



    public void clearCart() {
        items.clear();
        totalAmount = 0.0;
        paymentMethod = null;
        System.out.println("ShoppingCart: Cart cleared.");
    }


    private void calculateTotal() {
        this.totalAmount = 0.0;
        for (CartItem item : items) {
            this.totalAmount += item.getSubtotal();
        }
    }


    public List<CartItem> getItems() {
        return items;

    }

    public double getTotalAmount() {

        return totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }


    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public int getItemCount() {
        return items.size();
    }
}