package com.lusilab.L.AromaCoffeeShop.controller;

import com.lusilab.L.AromaCoffeeShop.entity.CoffeeOrder;
import com.lusilab.L.AromaCoffeeShop.cart.CartItem;
import com.lusilab.L.AromaCoffeeShop.cart.ShoppingCart;
import com.lusilab.L.AromaCoffeeShop.entity.PaymentMethod;
import com.lusilab.L.AromaCoffeeShop.service.CoffeeOrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/checkout")
@SessionAttributes("shoppingCart")
public class CheckoutController {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @GetMapping("/form")
    public String checkoutForm(@ModelAttribute("shoppingCart") ShoppingCart cart, Model model) {


        if (cart == null || cart.getItems().isEmpty()) {
            logger.warn("Attempt to checkout with null or empty cart. Redirecting to /coffees.");
            return "redirect:/coffees";
        }
        model.addAttribute("paymentMethods", PaymentMethod.values());
        logger.debug("Displaying checkout form. Cart has {} items.", cart.getItemCount());
        return "checkout-form";
    }

    @PostMapping("/process")
    public String processCheckout(@ModelAttribute("shoppingCart") ShoppingCart cart,
                                  @RequestParam(name = "paymentMethod", required = false) PaymentMethod paymentMethodFromForm,
                                  SessionStatus sessionStatus,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        if (cart == null || cart.getItems().isEmpty()) {
            logger.warn("Processing checkout with null or empty cart.");
            redirectAttributes.addFlashAttribute("message_error", "Your Cart is Empty or not found.");
            return "redirect:/cart/view";
        }

        if (paymentMethodFromForm == null) {
            logger.warn("Payment method not selected during checkout.");

            model.addAttribute("message_error_payment", "Please choose your Payment Method.");
            model.addAttribute("paymentMethods", PaymentMethod.values());

            return "checkout-form";
        }
        cart.setPaymentMethod(paymentMethodFromForm);

        List<Long> savedOrderIds = new ArrayList<>();
        String transactionId = "TRANS-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();

        logger.info("Processing checkout for cart. Transaction ID (virtual): {}. Items: {}. Payment Method: {}",
                transactionId, cart.getItemCount(), cart.getPaymentMethod());

        for (CartItem item : cart.getItems()) {
            if (item.getCoffee() == null) {
                logger.error("Skipping cart item with null coffee during order creation. CartItemID: {}", item.getCartItemId());
                continue;
            }

            CoffeeOrder dbOrder = new CoffeeOrder();
            dbOrder.setCoffee(item.getCoffee());
            dbOrder.setQuantity(item.getQuantity());
            dbOrder.setCoffeeSize(item.getCoffeeSize());
            dbOrder.setPaymentMethod(cart.getPaymentMethod());


            dbOrder.setExtraSugar(item.isExtraSugar());
            dbOrder.setExtraChocolate(item.isExtraChocolate());
            dbOrder.setExtraCinnamon(item.isExtraCinnamon());
            dbOrder.setExtraMilk(item.isExtraMilk());
            dbOrder.setExtraCookies(item.isExtraCookies());
            dbOrder.setExtraCaramel(item.isExtraCaramel());
            dbOrder.setExtraVanillie(item.isExtraVanillie());
            dbOrder.setExtraCacao(item.isExtraCacao());
            dbOrder.setExtraWhippedCream(item.isExtraWhippedCream());

            try {
                CoffeeOrder savedDbOrder = coffeeOrderService.saveOrder(dbOrder);
                if (savedDbOrder != null && savedDbOrder.getOrderId() != null) {
                    savedOrderIds.add(savedDbOrder.getOrderId());
                    logger.debug("Saved CoffeeOrder entity with ID: {} for cart item ID: {}", savedDbOrder.getOrderId(), item.getCartItemId());
                } else {
                    logger.error("Failed to save order for cart item ID: {} or OrderId was null after save.", item.getCartItemId());
                    redirectAttributes.addFlashAttribute("message_error", "Error during the processing of a part of the order. Contact support.");
                    return "redirect:/cart/view";
                }
            } catch (Exception e) {
                logger.error("Exception during saving order for cart item ID: {}. Error: {}", item.getCartItemId(), e.getMessage(), e);
                redirectAttributes.addFlashAttribute("message_error", "Unexpected error while saving the order. Please try again.");
                return "redirect:/cart/view";
            }
        }

        if (savedOrderIds.isEmpty() && !cart.getItems().isEmpty()) {
            logger.error("No orders were saved successfully, but the cart was not empty. Transaction ID: {}", transactionId);
            redirectAttributes.addFlashAttribute("message_error", "No item could be processed. Please try again.");

            return "redirect:/checkout/form";
        }

        logger.info("Checkout processed successfully. Transaction ID (virtual): {}. Saved DB Order IDs: {}", transactionId, savedOrderIds);

        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("message_success", "Your order has been processed successfully!");
        redirectAttributes.addFlashAttribute("processedOrderIds", savedOrderIds);
        redirectAttributes.addFlashAttribute("transactionIdForView", transactionId);

        return "redirect:/checkout/confirmation";
    }

    @GetMapping("/confirmation")
    public String checkoutConfirmation(Model model) {

        if (!model.containsAttribute("transactionIdForView")) {
            logger.warn("Accessing /checkout/confirmation without necessary processed order data (e.g., transactionIdForView). Redirecting to home.");
            return "redirect:/";
        }
        logger.debug("Displaying checkout confirmation page for transaction ID: {}", model.getAttribute("transactionIdForView"));
        return "checkout-confirmation";
    }
}