package com.supunsathsara.pizzacreed.service;

import com.supunsathsara.pizzacreed.dao.Pizza;

import java.util.List;

public interface PizzaService {
    List<Pizza> getAllPizzas();

    Pizza getPizzaById(Long pizzaId);

    Pizza savePizza(Pizza pizza);

    void deletePizza(Long pizzaId);

    void editPizza(Long id, Pizza editedPizza);

    int getPizzaCount();
}
