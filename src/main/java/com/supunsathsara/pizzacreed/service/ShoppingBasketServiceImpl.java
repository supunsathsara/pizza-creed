package com.supunsathsara.pizzacreed.service;

import com.supunsathsara.pizzacreed.dao.BasketItem;
import com.supunsathsara.pizzacreed.dao.Order;
import com.supunsathsara.pizzacreed.dao.Pizza;
import com.supunsathsara.pizzacreed.dao.ShoppingBasket;
import com.supunsathsara.pizzacreed.repository.OrderRepository;
import com.supunsathsara.pizzacreed.repository.PizzaRepository;
import com.supunsathsara.pizzacreed.repository.ShoppingBasketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingBasketServiceImpl implements ShoppingBasketService {

    @Autowired
    private ShoppingBasketRepository basketRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private ShoppingBasketRepository basketItemRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public ShoppingBasket createBasket() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setStatus("EMPTY");
        return basketRepository.save(shoppingBasket);
    }

    @Override
    public ShoppingBasket getBasketById(Long basketId) {
       // return basketRepository.findById(basketId).orElse(null);
        Optional<ShoppingBasket> basketOptional = basketRepository.findById(basketId);

        if (basketOptional.isPresent()) {
            return basketOptional.get();
        } else {
            throw new EntityNotFoundException("Shopping basket not found with id: " + basketId);
        }
    }

    @Override
    public void addItemToBasket(Long basketId, Long pizzaId, int quantity) {
        ShoppingBasket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));

        //check if the basket status is COMPLETE
        if (basket.getStatus().equals("COMPLETE")) {
            throw new RuntimeException("Basket is already checked out");
        }

        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(() -> new RuntimeException("Pizza not found"));

        BasketItem item = new BasketItem();
        item.setQuantity(quantity);
        item.setPizza(pizza);
        item.setShoppingBasket(basket);

        basket.getItems().add(item);
        basket.setTotalAmount(basket.getTotalAmount() + (pizza.getPrice() * quantity));
        basket.setStatus("IN_PROGRESS");


        basketRepository.save(basket);
    }

    @Override
    public void removeItemFromBasket(Long basketId, Long itemId) {
        ShoppingBasket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));

        //check if the basket status is COMPLETE
        if (basket.getStatus().equals("COMPLETE")) {
            throw new RuntimeException("Basket is already checked out");
        }

        basket.getItems().removeIf(item -> item.getId().equals(itemId));

        basket.setTotalAmount(basket.getItems().stream()
                .mapToDouble(item -> item.getPizza().getPrice() * item.getQuantity())
                .sum());

        if (basket.getItems().isEmpty()) {
            basket.setStatus("EMPTY");
        }

        basketRepository.save(basket);
    }

    @Override
    public void clearBasket(Long basketId) {
        ShoppingBasket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));

        basket.getItems().clear();

        basket.setTotalAmount(0);
        basket.setStatus("EMPTY");

        basketRepository.save(basket);
    }

    @Override
    public Order checkout(Long basketId) {
        ShoppingBasket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));

        //get the total amount of the basket
        double totalAmount = basket.getItems().stream()
                .mapToDouble(item -> item.getPizza().getPrice() * item.getQuantity())
                .sum();

        Order order = new Order();
        order.setShoppingBasket(basket);
        order.setTotalAmount(totalAmount);

        basket.setStatus("COMPLETE");

        return orderRepository.save(order);

    }
}
