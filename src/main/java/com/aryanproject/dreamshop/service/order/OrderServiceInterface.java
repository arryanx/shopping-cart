package com.aryanproject.dreamshop.service.order;


import com.aryanproject.dreamshop.dto.OrderDto;
import com.aryanproject.dreamshop.model.Order;

import java.util.List;

public interface OrderServiceInterface {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
