package com.lusilab.L.AromaCoffeeShop.contrroller;

import com.lusilab.L.AromaCoffeeShop.entity.Admin;
import com.lusilab.L.AromaCoffeeShop.entity.Coffee;
import com.lusilab.L.AromaCoffeeShop.service.AdminService;
import com.lusilab.L.AromaCoffeeShop.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CoffeeService coffeService;


    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/signup";
    }

    @PostMapping("/signup")
    public String registerAdmin(@ModelAttribute("admin") Admin admin, Model model) {
        if (adminService.existsByEmail(admin.getEmail())) {
            model.addAttribute("error", "This email already exists");
            return "admin/signup";
        }
        adminService.saveAdmin(admin);
        return "redirect:/admins/login";
    }


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginAdmin(@ModelAttribute("admin") Admin admin, Model model) {
        Optional<Admin> loggedIn = adminService.login(admin.getEmail(), admin.getPassword());
        if (loggedIn.isPresent()) {
            return "redirect:/admins/dashboard";
        }
        model.addAttribute("error", "Invalid email or password");
        return "admins/login";
    }

    @GetMapping
    public String listCoffees(Model model) {
        model.addAttribute("coffees", coffeeService.getAllCoffees());
        return "admin/coffee-list";
    }
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("coffee", new Coffee());
        return "admin/coffee-form";
    }
    @PostMapping("/save")
    public String saveCoffee(@ModelAttribute Coffee coffee) {
        coffeeService.saveCoffee(coffee);
        return "redirect:/admin/coffees";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        model.addAttribute("coffee", coffee);
        return "admin/coffee-form";
    }
    @GetMapping("/delete/{id}")
    public String deleteCoffee(@PathVariable Long id) {
        coffeeService.deleteCoffee(id);
        return "redirect:/admin/coffees";
    }
}



}
