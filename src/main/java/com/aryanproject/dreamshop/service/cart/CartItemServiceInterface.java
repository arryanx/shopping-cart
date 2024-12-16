package com.aryanproject.dreamshop.service.cart;

import com.aryanproject.dreamshop.model.CartItem;

public interface CartItemServiceInterface {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
