package com.lusilab.L.AromaCoffeeShop.controller;

import com.lusilab.L.AromaCoffeeShop.cart.ShoppingCart;
import com.lusilab.L.AromaCoffeeShop.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("shoppingCart")
public class CoffeeMenuController {

    @Autowired
    private CoffeeService coffeeService;


    @ModelAttribute("shoppingCart")
    public ShoppingCart getShoppingCart() {
        return new ShoppingCart();
    }

    @GetMapping("/coffees")
    public String showCoffeeMenu(Model model, @ModelAttribute("shoppingCart") ShoppingCart cart) {
        model.addAttribute("coffees", coffeeService.getAllCoffees());

        return "menu";
    }
}