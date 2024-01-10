package com.supunsathsara.pizzacreed.exception;

public class BasketAlreadyCheckedOutException extends RuntimeException {
    public BasketAlreadyCheckedOutException(String message) {
        super(message);
    }

}
