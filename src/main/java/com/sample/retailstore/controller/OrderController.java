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
     * @return
     */
    @GetMapping("/list")
    public OrderResponse getOrderList() {
        return orderService.getOrderList();
    }

    /**
     * Get Order by Id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    /**
     * Save or Update order
     *
     * @param request
     * @return
     */
    @PostMapping("/save")
    public OrderResponse placeOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.placeOrder(request);
    }


    /**
     * Cancel order
     *
     * @param request
     * @return
     */
    @PutMapping("/cancel")
    public OrderResponse cancelOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.cancelOrder(request);
    }


    /**
     * Update order
     *
     * @param request
     * @return
     */
    @PutMapping("/update")
    public OrderResponse updateOrderStatus(@Valid @RequestBody UpdateOrderRequest request) {
        return orderService.updateOrderStatus(request);
    }

}
