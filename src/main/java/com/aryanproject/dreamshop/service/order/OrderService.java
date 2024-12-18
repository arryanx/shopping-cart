package com.aryanproject.dreamshop.service.order;

import com.aryanproject.dreamshop.dto.OrderDto;
import com.aryanproject.dreamshop.enums.OrderStatus;
import com.aryanproject.dreamshop.exceptions.ResourceNotFoundException;
import com.aryanproject.dreamshop.model.*;
import com.aryanproject.dreamshop.repository.OrderRepository;
import com.aryanproject.dreamshop.repository.ProductRepository;
import com.aryanproject.dreamshop.service.cart.CartServiceInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartServiceInterface cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart= cartService.getCartByUserId(userId);

        Order order=creatOrder(cart);
        List<OrderItem> orderItemList= createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder=orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    private Order creatOrder(Cart cart){
        Order order= new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems()
                .stream()
                .map(item -> {
                    Product product=item.getProduct();
                    product.setInventory(product.getInventory()-item.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            item.getQuantity(),
                            item.getUnitPrice()
                    );
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList){
        return orderItemList.stream()
                .map(item->item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> orders= orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    public OrderDto convertToDto(Order order){
        return modelMapper.map(order,OrderDto.class);
    }
}
