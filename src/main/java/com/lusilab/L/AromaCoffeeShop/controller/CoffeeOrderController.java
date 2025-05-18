package com.lusilab.L.AromaCoffeeShop.controller;

import com.lusilab.L.AromaCoffeeShop.entity.Coffee;
import com.lusilab.L.AromaCoffeeShop.entity.CoffeeSize;
import com.lusilab.L.AromaCoffeeShop.entity.PaymentMethod;
import com.lusilab.L.AromaCoffeeShop.service.CoffeeOrderService;
import com.lusilab.L.AromaCoffeeShop.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/orders")
public class CoffeeOrderController {
    @Autowired
    private CoffeeOrderService coffeeOrderService;
    @Autowired
    private CoffeeService coffeeService;
    @GetMapping
    public String listOrder(Model model) {
        model.addAttribute("orders", coffeeOrderService.getAllOrder());
        return "coffees/list";
    }
    @GetMapping("/new")
    public String showOrderForm(Model model) {
        model.addAttribute("coffees", coffeeService.getAllCoffees());
        model.addAttribute("sizes", CoffeeSize.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "order-form";
    }
    @PostMapping("/save")
    public String saveOrder(
            @RequestParam Coffee coffee,
            @RequestParam CoffeeSize coffeeSizesize,
            @RequestParam int quantity,
            @RequestParam(required = false, defaultValue = "false") Boolean extraSugar,
            @RequestParam(required = false, defaultValue = "false") Boolean extraChocolate,
            @RequestParam(required = false, defaultValue = "false") Boolean extraCinnamon,
            @RequestParam(required = false, defaultValue = "false") Boolean extraMilk,
            @RequestParam(required = false, defaultValue = "false") Boolean extraVanillie,
            @RequestParam(required = false, defaultValue = "false") Boolean extraCookies,
            @RequestParam(required = false, defaultValue = "false") Boolean extraCaramel,
            @RequestParam(required = false, defaultValue = "false") Boolean extraCacao,
            @RequestParam(required = false, defaultValue = "false") Boolean extraWhippedCream,
            @RequestParam PaymentMethod paymentMethod
    ) {
        coffeeOrderService.createNewOrder(coffee, coffeeSizesize, quantity,
         paymentMethod,
        extraSugar, extraChocolate, extraCinnamon,
                 extraMilk,  extraVanillie,  extraCookies,
                 extraCaramel,  extraCacao, extraWhippedCream);
        return "redirect:/orders";
    }
}