package com.supunsathsara.pizzacreed.repository;

import com.supunsathsara.pizzacreed.dao.ShoppingBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, Long> {

}
