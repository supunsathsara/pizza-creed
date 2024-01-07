package com.supunsathsara.pizzacreed.service;

import com.supunsathsara.pizzacreed.dao.Pizza;
import com.supunsathsara.pizzacreed.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public List<Pizza> getAllPizzas() {

        return pizzaRepository.findAll();
    }

    @Override
    public Pizza getPizzaById(Long pizzaId) {
        return pizzaRepository.findById(pizzaId).orElse(null);
    }

    @Override
    public Pizza savePizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public void deletePizza(Long pizzaId) {
        pizzaRepository.deleteById(pizzaId);
    }
}
