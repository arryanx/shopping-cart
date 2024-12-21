package com.aryanproject.dreamshop.service.cart;

import com.aryanproject.dreamshop.model.Cart;
import com.aryanproject.dreamshop.model.User;

import java.math.BigDecimal;

public interface CartServiceInterface {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
