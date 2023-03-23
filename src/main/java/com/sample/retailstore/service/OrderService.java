package com.sample.retailstore.service;

import com.sample.retailstore.request.OrderRequest;
import com.sample.retailstore.request.UpdateOrderRequest;
import com.sample.retailstore.response.OrderResponse;

/**
 * @author irfan.nagoo
 */
public interface OrderService {

    /**
     * Get list of Purchase orders
     *
     * @return
     */
    OrderResponse getOrderList();

    /**
     * Get Purchase order by id
     *
     * @param id
     * @return
     */
    OrderResponse getOrderById(Long id);

    /**
     * Get Purchase orders by user id
     *
     * @param userId
     * @return
     */
    OrderResponse getOrdersByUserId(Long userId);

    /**
     * Place a purchase order
     *
     * @param request
     * @return
     */
    OrderResponse placeOrder(OrderRequest request);

    /**
     * Cancel a purchase order
     *
     * @param request
     * @return
     */
    OrderResponse cancelOrder(OrderRequest request);

    /**
     * Update order
     *
     * @param request
     * @return
     */
    OrderResponse updateOrderStatus(UpdateOrderRequest request);

}
