package com.supunsathsara.pizzacreed.controller;

import com.supunsathsara.pizzacreed.dao.Pizza;
import com.supunsathsara.pizzacreed.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * PizzaController
 */

@Controller
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/")
    public String showHome(){
        return "index";
    }

    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<Pizza> pizzas = pizzaService.getAllPizzas();
        model.addAttribute("pizzas", pizzas);

        return "pizza";
    }

    @GetMapping("/addPizza")
    public String showAddPizzaForm(Model model) {
        model.addAttribute("newPizza", new Pizza()); // Create a new Pizza object to bind the form data
        return "addPizza";
    }

    @PostMapping("/addPizza")
    public String addPizza(Pizza newPizza) {
        pizzaService.savePizza(newPizza);
        return "redirect:/menu";
    }

}
