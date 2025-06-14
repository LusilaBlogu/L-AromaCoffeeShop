package com.lusilab.L.AromaCoffeeShop.controller;

import com.lusilab.L.AromaCoffeeShop.cart.CartItem;
import com.lusilab.L.AromaCoffeeShop.cart.ShoppingCart;
import com.lusilab.L.AromaCoffeeShop.entity.Coffee;
import com.lusilab.L.AromaCoffeeShop.entity.CoffeeSize;
import com.lusilab.L.AromaCoffeeShop.service.CoffeeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Për validim të avancuar (opsionale)
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("shoppingCart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CoffeeService coffeeService;


    @ModelAttribute("shoppingCart")
    public ShoppingCart initializeShoppingCart() {
        logger.debug("Initializing new ShoppingCart in session as it was not found.");
        return new ShoppingCart();
    }

    @GetMapping("/cart/configure/{coffeeId}")
    public String configureCartItemForm(@PathVariable Long coffeeId,
                                        Model model,
                                        RedirectAttributes redirectAttributes,
                                        @ModelAttribute("shoppingCart") ShoppingCart cart) {
        Coffee coffee = coffeeService.getCoffeeById(coffeeId);
        if (coffee == null) {
            logger.warn("Attempted to configure a non-existent coffee with ID: {}", coffeeId);
            redirectAttributes.addFlashAttribute("message_error", "Coffee not found.");
            return "redirect:/coffees";
        }


        CartItem cartItemForForm = new CartItem();


        model.addAttribute("coffeeToConfigure", coffee);
        model.addAttribute("cartItemFormData", cartItemForForm);
        model.addAttribute("coffeeSizes", CoffeeSize.values());

        logger.debug("Showing configuration form for coffee ID: {}", coffeeId);
        return "configure-cart-item";
    }

    @PostMapping("/cart/add")
    public String addItemToCart(@ModelAttribute("cartItemFormData") CartItem cartItemFormData,
                                BindingResult bindingResult,
                                @RequestParam("configuredCoffeeId") Long configuredCoffeeId,
                                @ModelAttribute("shoppingCart") ShoppingCart cart,
                                RedirectAttributes redirectAttributes) {

        Coffee coffee = coffeeService.getCoffeeById(configuredCoffeeId);
        if (coffee == null) {
            logger.error("Failed to add to cart. Coffee with ID {} not found.", configuredCoffeeId);
            redirectAttributes.addFlashAttribute("message_error", "Coffee not found!");
            return "redirect:/coffees";
        }


        if (cartItemFormData.getQuantity() < 1) {
            logger.warn("Invalid quantity ({}) submitted for coffee ID {}. Setting to 1.", cartItemFormData.getQuantity(), configuredCoffeeId);
            cartItemFormData.setQuantity(1);
        }
        if (cartItemFormData.getCoffeeSize() == null) {
            logger.warn("CoffeeSize not selected for coffee ID {}. This might cause issues.", configuredCoffeeId);
        }


        CartItem finalCartItem = new CartItem();
        finalCartItem.setCoffee(coffee);
        finalCartItem.setQuantity(cartItemFormData.getQuantity());
        finalCartItem.setCoffeeSize(cartItemFormData.getCoffeeSize());


        finalCartItem.setExtraSugar(cartItemFormData.isExtraSugar());
        finalCartItem.setExtraChocolate(cartItemFormData.isExtraChocolate());
        finalCartItem.setExtraCinnamon(cartItemFormData.isExtraCinnamon());
        finalCartItem.setExtraMilk(cartItemFormData.isExtraMilk());
        finalCartItem.setExtraCookies(cartItemFormData.isExtraCookies());
        finalCartItem.setExtraCaramel(cartItemFormData.isExtraCaramel());
        finalCartItem.setExtraVanillie(cartItemFormData.isExtraVanillie());
        finalCartItem.setExtraCacao(cartItemFormData.isExtraCacao());
        finalCartItem.setExtraWhippedCream(cartItemFormData.isExtraWhippedCream());

        cart.addItem(finalCartItem);

        logger.info("Item added to cart: Coffee ID {}, Name: {}. Quantity: {}, Size: {}. Cart now has {} items. Total: {}",
                coffee.getId(), coffee.getName(), finalCartItem.getQuantity(), finalCartItem.getCoffeeSize(),
                cart.getItems().size(), cart.getTotalAmount());

        redirectAttributes.addFlashAttribute("message_success",
                finalCartItem.getCoffeeName() + " (x" + finalCartItem.getQuantity() + ") add to Cart!");
        return "redirect:/coffees";
    }

    @GetMapping("/cart/view")
    public String viewCart(Model model, @ModelAttribute("shoppingCart") ShoppingCart cart) {

        logger.debug("Viewing cart with {} items. Total: {}", cart.getItems().size(), cart.getTotalAmount());
        return "shopping-cart";
    }

    @PostMapping("/cart/update")
    public String updateCartItemQuantity(@RequestParam String cartItemId,
                                         @RequestParam int quantity,
                                         @ModelAttribute("shoppingCart") ShoppingCart cart) {
        logger.info("Updating quantity for cartItemId: {} to new quantity: {}", cartItemId, quantity);
        cart.updateItemQuantity(cartItemId, quantity);
        return "redirect:/cart/view";
    }

    @GetMapping("/cart/remove/{cartItemId}")
    public String removeItemFromCart(@PathVariable String cartItemId,
                                     @ModelAttribute("shoppingCart") ShoppingCart cart) {
        logger.info("Attempting to remove cartItemId: {}", cartItemId);
        boolean removed = cart.removeItem(cartItemId);
        if (removed) {
            logger.info("Successfully removed cartItemId: {}", cartItemId);
        } else {
            logger.warn("Failed to remove cartItemId (not found?): {}", cartItemId);
        }
        return "redirect:/cart/view";
    }
}