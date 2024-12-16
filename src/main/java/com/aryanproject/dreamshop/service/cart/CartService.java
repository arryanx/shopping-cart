package com.aryanproject.dreamshop.service.cart;

import com.aryanproject.dreamshop.exceptions.ProductNotFoundException;
import com.aryanproject.dreamshop.model.Cart;
import com.aryanproject.dreamshop.repository.CartItemRepository;
import com.aryanproject.dreamshop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements CartServiceInterface{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator= new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart= cartRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Cart not found"));
        BigDecimal totalAmount= cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart= getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart(){
        Cart newCart= new Cart();
        Long newCartId= cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }
}
