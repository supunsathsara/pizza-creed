package com.supunsathsara.pizzacreed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * PizzaController
 */

@Controller
public class PizzaController {

    @GetMapping("/")
    public String showHome(){
        return "index";
    }
}
