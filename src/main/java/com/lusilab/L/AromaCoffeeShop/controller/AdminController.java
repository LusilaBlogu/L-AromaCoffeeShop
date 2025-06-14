package com.lusilab.L.AromaCoffeeShop.controller;

import com.lusilab.L.AromaCoffeeShop.entity.Admin;
import com.lusilab.L.AromaCoffeeShop.entity.Coffee;
import com.lusilab.L.AromaCoffeeShop.service.AdminService;
import com.lusilab.L.AromaCoffeeShop.service.CoffeeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admins")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {

        if (!model.containsAttribute("admin")) {
            model.addAttribute("admin", new Admin());
        }
        logger.debug("Showing admin login form.");
        return "admins/login";
    }

    @PostMapping("/login")
    public String loginAdmin(@ModelAttribute("admin") Admin admin,
                             RedirectAttributes redirectAttributes, Model model) {


        logger.info("Attempting login for email: {}", admin.getEmail());
        Optional<Admin> loggedInAdmin = adminService.login(admin.getEmail(), admin.getPassword());

        if (loggedInAdmin.isPresent()) {
            logger.info("Admin login successful for email: {}", admin.getEmail());

            return "redirect:/admins/dashboard";
        } else {
            logger.warn("Admin login failed for email: {}", admin.getEmail());
            redirectAttributes.addFlashAttribute("error", "Email or password is not correct.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/admins/login";
        }
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        if (!model.containsAttribute("admin")) {
            model.addAttribute("admin", new Admin());
        }
        logger.debug("Showing admin signup form.");
        return "admins/signup";
    }

    @PostMapping("/signup")
    public String registerAdmin(@ModelAttribute("admin") Admin admin, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model) {


        if (admin.getName() == null || admin.getName().trim().isEmpty() ||
                admin.getUsername() == null || admin.getUsername().trim().isEmpty() ||
                admin.getEmail() == null || admin.getEmail().trim().isEmpty() ||
                admin.getPassword() == null || admin.getPassword().isEmpty()) {

            redirectAttributes.addFlashAttribute("error", "Please fill in all fields.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/admins/signup";
        }


        logger.info("Attempting to register admin with email: {}", admin.getEmail());
        if (adminService.existsByEmail(admin.getEmail())) {
            logger.warn("Admin signup failed. Email already exists: {}", admin.getEmail());
            redirectAttributes.addFlashAttribute("error", "This email already exist.");
            redirectAttributes.addFlashAttribute("admin", admin);
            return "redirect:/admins/signup";
        }

        adminService.saveAdmin(admin);
        logger.info("Admin registered successfully with email: {}", admin.getEmail());
        redirectAttributes.addFlashAttribute("signupSuccess", "The registration was completed successfully! You can log in now.");
        return "redirect:/admins/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        logger.debug("Accessing admin dashboard.");
        return "admins/dashboard";
    }


    @GetMapping("/coffees/list")
    public String listCoffees(Model model) {
        model.addAttribute("coffees", coffeeService.getAllCoffees());
        logger.debug("Listing all coffees for admin.");
        return "admins/list-coffees";
    }

    @GetMapping("/coffees/form")
    public String showNewCoffeeForm(Model model) {
        model.addAttribute("coffee", new Coffee());
        model.addAttribute("pageTitle", "Add new Coffee");
        logger.debug("Showing new coffee form.");
        return "admins/form-coffee";
    }

    @GetMapping("/coffees/edit/{id}")
    public String showEditCoffeeForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        if (coffee == null) {
            logger.warn("Edit coffee failed. Coffee not found with ID: {}", id);
            redirectAttributes.addFlashAttribute("error_message", "Coffee with ID " + id + " is not found.");
            return "redirect:/admins/coffees/list";
        }
        model.addAttribute("coffee", coffee);
        model.addAttribute("pageTitle", "Edit: " + coffee.getName());
        logger.debug("Showing edit coffee form for ID: {}", id);
        return "admins/form-coffee";
    }

    @PostMapping("/coffees/save")
    public String saveOrUpdateCoffee(@ModelAttribute("coffee") Coffee coffee, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Model model) {


        if (coffee.getPrice() < 0) {
            logger.warn("Attempt to save coffee with negative price: {}", coffee.getPrice());
            redirectAttributes.addFlashAttribute("error_form", "Price cannot be negative.");

            if (coffee.getId() == null) {
                return "redirect:/admins/coffees/form";
            } else {
                return "redirect:/admins/coffees/edit/" + coffee.getId();
            }
        }


        boolean isNew = coffee.getId() == null;
        String action = isNew ? "shtua" : "modifikua";
        logger.info("Attempting to save coffee (isNew={}): {}", isNew, coffee.getName());

        coffeeService.saveCoffee(coffee);
        logger.info("Coffee {} successfully: {}", action, coffee.getName());
        redirectAttributes.addFlashAttribute("success_message", "Coffee: '" + coffee.getName() + "' is added  succesfully!");
        return "redirect:/admins/coffees/list";
    }

    @GetMapping("/coffees/delete/{id}")
    public String deleteCoffee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        logger.info("Attempting to delete coffee with ID: {}", id);
        try {
            Coffee coffeeToDelete = coffeeService.getCoffeeById(id);
            if (coffeeToDelete != null) {
                coffeeService.deleteCoffee(id);
                logger.info("Coffee with ID: {} deleted successfully.", id);
                redirectAttributes.addFlashAttribute("success_message", "Coffee '" + coffeeToDelete.getName() + "' deleted succesfully!");
            } else {
                logger.warn("Delete coffee failed. Coffee not found with ID: {}", id);
                redirectAttributes.addFlashAttribute("error_message", "Coffee not found.");
            }
        } catch (Exception e) {
            logger.error("Error deleting coffee with ID: {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("error_message", "The coffee could not be hidden. It may be related to other data or an error has occurred.");
        }
        return "redirect:/admins/coffees/list";
    }
}