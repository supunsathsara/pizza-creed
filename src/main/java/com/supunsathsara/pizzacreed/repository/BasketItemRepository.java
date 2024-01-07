package com.supunsathsara.pizzacreed.repository;

import com.supunsathsara.pizzacreed.dao.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

}
