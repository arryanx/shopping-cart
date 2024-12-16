package com.aryanproject.dreamshop.service.cart;

import com.aryanproject.dreamshop.exceptions.ResourceNotFoundException;
import com.aryanproject.dreamshop.model.Cart;
import com.aryanproject.dreamshop.model.CartItem;
import com.aryanproject.dreamshop.model.Product;
import com.aryanproject.dreamshop.repository.CartItemRepository;
import com.aryanproject.dreamshop.repository.CartRepository;
import com.aryanproject.dreamshop.service.product.ProductServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements CartItemServiceInterface{

    private final CartRepository cartRepository;
    private final ProductServiceInterface productService;
    private final CartServiceInterface cartService;
    private final CartItemRepository cartItemRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart= cartService.getCart(cartId);
        Product product= productService.getProductById(productId);
        CartItem cartItem= cart.getItems().
                stream().
                filter(item -> item.getProduct().getId().equals(productId)).
                findFirst().
                orElse(new CartItem());
        if(cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart= cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId)).findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });

        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return  cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
