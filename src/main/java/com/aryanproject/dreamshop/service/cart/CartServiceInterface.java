package com.aryanproject.dreamshop.service.cart;

import com.aryanproject.dreamshop.model.Cart;

import java.math.BigDecimal;

public interface CartServiceInterface {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}
