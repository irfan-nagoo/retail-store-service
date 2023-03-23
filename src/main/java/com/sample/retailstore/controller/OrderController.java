package com.sample.retailstore.controller;

import com.sample.retailstore.request.OrderRequest;
import com.sample.retailstore.request.UpdateOrderRequest;
import com.sample.retailstore.response.OrderResponse;
import com.sample.retailstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author irfan.nagoo
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Get Order list
     *
     * @return list of orders
     */
    @GetMapping("/list")
    public OrderResponse getOrderList() {
        return orderService.getOrderList();
    }

    /**
     * Get Order by Id
     *
     * @param id order id
     * @return order response
     */
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    /**
     * Get Order by user Id
     *
     * @param userId user Id
     * @return order response
     */
    @GetMapping("/user/{id}")
    public OrderResponse getOrdersByUserId(@PathVariable("id") Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    /**
     * Save order
     *
     * @param request new order request
     * @return order response
     */
    @PostMapping("/save")
    public OrderResponse placeOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }


    /**
     * Cancel order
     *
     * @param request can order request
     * @return response
     */
    @PutMapping("/cancel")
    public OrderResponse cancelOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.cancelOrder(request);
    }


    /**
     * Update order Status
     *
     * @param request update order status request
     * @return status response
     */
    @PutMapping("/update")
    public OrderResponse updateOrderStatus(@Valid @RequestBody UpdateOrderRequest request) {
        return orderService.updateOrderStatus(request);
    }

}
