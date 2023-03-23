package com.sample.retailstore.service.impl;

import com.sample.retailstore.cache.StatusCache;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.domain.BillingObject;
import com.sample.retailstore.domain.ItemObject;
import com.sample.retailstore.domain.OrderDetailObject;
import com.sample.retailstore.domain.OrderObject;
import com.sample.retailstore.entity.Order;
import com.sample.retailstore.entity.Status;
import com.sample.retailstore.entity.User;
import com.sample.retailstore.exception.ActionNotAllowedException;
import com.sample.retailstore.exception.RecordNotFoundException;
import com.sample.retailstore.mapper.OrderMapper;
import com.sample.retailstore.repository.OrderRepository;
import com.sample.retailstore.repository.UserRepository;
import com.sample.retailstore.request.OrderRequest;
import com.sample.retailstore.request.UpdateOrderRequest;
import com.sample.retailstore.response.OrderResponse;
import com.sample.retailstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.sample.retailstore.constants.MessageConstants.*;
import static com.sample.retailstore.constants.RetailStoreConstants.ORDER_STATUS;

/**
 * @author irfan.nagoo
 */

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final OrderMapper orderMapper;

    private final StatusCache statusCache;

    private final Random randomNumberGen = new Random();

    @Override
    public OrderResponse getOrderList() {
        LOGGER.info("Processing order list request");
        List<OrderObject> orderObjList = StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(orderMapper::orderToOrderObject)
                .collect(Collectors.toList());
        OrderResponse response = new OrderResponse(HttpStatus.OK.name(), String.format(LIST_SUCCESS_MSG, orderObjList.size()));
        response.setOrders(orderObjList);
        return response;
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        LOGGER.info("Processing order by Id");
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            OrderResponse response = new OrderResponse(HttpStatus.OK.name(), GET_SUCCESS_MSG);
            response.getOrders().add(orderMapper.orderToOrderObject(order.get()));
            return response;
        } else {
            throw new RecordNotFoundException(ORDER_NOT_FOUND);
        }
    }

    @Override
    public OrderResponse getOrdersByUserId(Long userId) {
        LOGGER.info("Processing order by user Id");
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RecordNotFoundException("User Not Found");
        }
        List<Order> orderList = orderRepository.findByUser(userOpt.get());
        if (!orderList.isEmpty()) {
            List<OrderObject> orderObjList = orderList.stream().map(orderMapper::orderToOrderObject)
                    .collect(Collectors.toList());
            OrderResponse response = new OrderResponse(HttpStatus.OK.name(), String.format(LIST_SUCCESS_MSG, orderObjList.size()));
            response.setOrders(orderObjList);
            return response;
        } else {
            throw new RecordNotFoundException(ORDER_NOT_FOUND);
        }
    }


    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        LOGGER.info("Processing new Order request");
        OrderObject orderObject = request.getOrder();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderDetailObject od : orderObject.getOrderDetails()) {
            ItemObject itemObject = od.getItem();
            long orderedQty = od.getQuantity();
            long itemQty = itemObject.getQuantity();
            totalAmount = totalAmount.add(itemObject.getPrice());
            if (orderedQty == itemQty) {
                itemObject.setStatus(StatusType.ITEM_OUT_OF_STOCK);
                itemObject.setQuantity(0L);
            } else if (itemQty > orderedQty) {
                itemObject.setQuantity(itemQty - orderedQty);
            } else {
                throw new IllegalArgumentException(String.format(ORDER_INVALID_QUANTITY_MSG, orderedQty, itemQty));
            }
        }
        if (null == orderObject.getOrderNumber()) {
            orderObject.setOrderNumber("OR" + randomNumberGen.nextInt(100000));
            orderObject.setStatus(StatusType.ORDER_PLACED);
        }

        BillingObject billingObject = orderObject.getBilling();
        if (null != billingObject.getTransId()) {
            billingObject.setStatus(StatusType.BILLING_PAID);
        } else {
            billingObject.setStatus(StatusType.BILLING_FAILED);
        }

        Order order = orderMapper.orderObjectToOrder(request.getOrder());
        // set the order entity in  the order details entities
        final Order finalOrder = order;
        order.getOrderDetails().forEach(od -> od.setOrder(finalOrder));
        order.getBilling().setOrder(finalOrder);

        // save order
        order = orderRepository.save(order);

        // build response message
        OrderResponse response = new OrderResponse(HttpStatus.OK.name(), ORDER_PLACED_MSG);
        response.getOrders().add(orderMapper.orderToOrderObject(order));
        return response;
    }

    @Override
    public OrderResponse cancelOrder(OrderRequest request) {
        LOGGER.info("Order cancellation request for orderId : {}", request.getOrder().getId());
        OrderObject orderObject = request.getOrder();
        if (StatusType.ORDER_PLACED == orderObject.getStatus()) {
            orderObject.setStatus(StatusType.ORDER_CANCELLED);
            for (OrderDetailObject od : orderObject.getOrderDetails()) {
                ItemObject itemObject = od.getItem();
                itemObject.setQuantity(od.getQuantity() + itemObject.getQuantity());
                if (StatusType.ITEM_OUT_OF_STOCK == itemObject.getStatus()) {
                    itemObject.setStatus(StatusType.ITEM_AVAILABLE);
                }
            }
            Order order = orderMapper.orderObjectToOrder(orderObject);
            final Order finalOrder = order;
            order.getOrderDetails().forEach(od -> od.setOrder(finalOrder));
            order.getBilling().setOrder(finalOrder);
            order = orderRepository.save(order);
            OrderResponse response = new OrderResponse(HttpStatus.OK.name(), ORDER_CANCELLED_MSG);
            response.getOrders().add(orderMapper.orderToOrderObject(order));
            return response;
        } else {
            throw new ActionNotAllowedException(String.format(ORDER_NOT_CANCELLED_MSG, orderObject.getStatus().getValue()));
        }
    }

    @Override
    public OrderResponse updateOrderStatus(UpdateOrderRequest request) {
        LOGGER.info("Updating order status for orderId: {}", request.getOrderId());
        Optional<Order> orderOptional = orderRepository.findById(request.getOrderId());
        if (orderOptional.isPresent() && StatusType.ORDER_CANCELLED != request.getStatus()) {
            Order order = orderOptional.get();
            Status status = statusCache.getStatus(ORDER_STATUS, request.getStatus().getValue());
            order.setStatus(status);
            order.setUpdatedBy(request.getUpdatedBy());
            order.setUpdateDate(LocalDateTime.now());
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException(String.format(ORDER_INVALID_INPUT_MSG, request.getOrderId(),
                    request.getStatus().getValue()));
        }
        return new OrderResponse(HttpStatus.OK.name(), ORDER_UPDATED_MSG);
    }


}
