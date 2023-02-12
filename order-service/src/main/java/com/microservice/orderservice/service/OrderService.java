package com.microservice.orderservice.service;

import com.microservice.orderservice.dto.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
