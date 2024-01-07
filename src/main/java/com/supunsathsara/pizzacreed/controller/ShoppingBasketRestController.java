package com.supunsathsara.pizzacreed.controller;

import com.supunsathsara.pizzacreed.converter.DTOConverter;
import com.supunsathsara.pizzacreed.dao.BasketItem;
import com.supunsathsara.pizzacreed.dao.Order;
import com.supunsathsara.pizzacreed.dao.ShoppingBasket;
import com.supunsathsara.pizzacreed.dto.ShoppingBasketDTO;
import com.supunsathsara.pizzacreed.service.ShoppingBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class ShoppingBasketRestController {

    @Autowired
    private ShoppingBasketService shoppingBasketService;

    @PostMapping("/api/baskets")
    public ShoppingBasket createBasket() {
        return shoppingBasketService.createBasket();
    }

    /*@GetMapping("/api/baskets/{basketId}")
    public ShoppingBasket getBasketById(@PathVariable Long basketId) {
       return shoppingBasketService.getBasketById(basketId);

    }*/

    @PostMapping("/api/baskets/{basketId}")
    public void addProductToBasket(@PathVariable Long basketId, @RequestBody BasketItem basketItem) {
        shoppingBasketService.addItemToBasket(basketId, basketItem.getPizza().getId(), basketItem.getQuantity());
    }

    @DeleteMapping("/api/baskets/{basketId}/items/{itemId}")
    public void removeItemFromBasket(@PathVariable Long basketId, @PathVariable Long itemId) {
        shoppingBasketService.removeItemFromBasket(basketId, itemId);
    }

    @GetMapping("/api/baskets/{basketId}/checkout")
    public ResponseEntity<Object> checkout(@PathVariable Long basketId) {
        Order order = shoppingBasketService.checkout(basketId);

        double totalAmount = order.getTotalAmount();

        // You can customize the response message and structure as needed
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Checkout successful");
        response.put("totalAmount", totalAmount);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/api/baskets/{basketId}")
    public ResponseEntity<ShoppingBasketDTO> getBasketById(@PathVariable Long basketId) {
        try {
            ShoppingBasket basket = shoppingBasketService.getBasketById(basketId);
            if (basket != null) {
                // Convert ShoppingBasket to ShoppingBasketDTO
                ShoppingBasketDTO basketDTO = DTOConverter.convertShoppingBasketToDTO(basket);
                return ResponseEntity.ok(basketDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*
    private ShoppingBasketDTO convertToDTO(ShoppingBasket basket) {
        List<BasketItemDTO> itemDTOs = basket.getItems().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ShoppingBasketDTO.builder()
                .id(basket.getId())
                .status(basket.getStatus())
                .totalAmount(basket.getTotalAmount())
                .items(itemDTOs)
                .build();
    }

    private BasketItemDTO convertToDTO(BasketItem item) {
        Pizza pizza = item.getPizza();

        return BasketItemDTO.builder()
                .id(item.getId())
                .pizzaId(pizza != null ? pizza.getId() : null)
                .pizzaName(pizza != null ? pizza.getName() : null)
                .pizzaSize(pizza != null ? pizza.getSize() : null)
                .pizzaPrice(pizza != null ? pizza.getPrice() : null)
                .quantity(item.getQuantity())
                .total(pizza != null ? pizza.getPrice() * item.getQuantity() : null)
                .build();
    }*/

}
