package com.sample.retailstore.service.impl;

import com.sample.retailstore.cache.StatusCache;
import com.sample.retailstore.constants.StatusType;
import com.sample.retailstore.domain.BillingObject;
import com.sample.retailstore.domain.ItemObject;
import com.sample.retailstore.domain.OrderDetailObject;
import com.sample.retailstore.domain.OrderObject;
import com.sample.retailstore.entity.Billing;
import com.sample.retailstore.entity.Order;
import com.sample.retailstore.entity.Status;
import com.sample.retailstore.entity.User;
import com.sample.retailstore.exception.ActionNotAllowedException;
import com.sample.retailstore.exception.RecordNotFoundException;
import com.sample.retailstore.mapper.OrderMapperImpl;
import com.sample.retailstore.repository.OrderRepository;
import com.sample.retailstore.repository.UserRepository;
import com.sample.retailstore.request.OrderRequest;
import com.sample.retailstore.request.UpdateOrderRequest;
import com.sample.retailstore.response.OrderResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static com.sample.retailstore.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderMapperImpl orderMapper;

    @Mock
    private StatusCache statusCache;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void getOrderList() {
        OrderObject orderObject = new OrderObject();
        orderObject.setOrderNumber("OR1234567");
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(new Order()));
        when(orderMapper.orderToOrderObject(any())).thenReturn(orderObject);
        OrderResponse response = orderService.getOrderList();
        assertEquals(1, response.getOrders().size());
        assertEquals("OR1234567", response.getOrders().get(0).getOrderNumber());
    }

    @Test
    void getOrderById_Success() {
        OrderObject orderObject = new OrderObject();
        orderObject.setOrderNumber("OR1234567");
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(new Order()));
        when(orderMapper.orderToOrderObject(any())).thenReturn(orderObject);
        OrderResponse response = orderService.getOrderById(1L);
        assertEquals(1, response.getOrders().size());
        assertEquals("OR1234567", response.getOrders().get(0).getOrderNumber());
    }

    @Test
    void getOrderById_Exception() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> orderService.getOrderById(1L));
        assertEquals(ORDER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getOrderByUserId_Success() {
        OrderObject orderObject = new OrderObject();
        orderObject.setOrderNumber("OR1234567");
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(orderRepository.findByUser(any())).thenReturn(Collections.singletonList(new Order()));
        when(orderMapper.orderToOrderObject(any())).thenReturn(orderObject);
        OrderResponse response = orderService.getOrdersByUserId(1L);
        assertEquals(1, response.getOrders().size());
        assertEquals("OR1234567", response.getOrders().get(0).getOrderNumber());
    }

    @Test
    void getOrderByUserId_Exception() {
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(orderRepository.findByUser(any())).thenReturn(Collections.emptyList());
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> orderService.getOrdersByUserId(1L));
        assertEquals(ORDER_NOT_FOUND, exception.getMessage());
    }


    @Test
    void placeOrder_Success() {
        OrderObject orderObject = getOrderObject();
        Order order = new Order();
        order.setOrderNumber("OR1234567");
        order.setStatus(new Status(1L, "any", "Placed"));
        order.setOrderDetails(new HashSet<>());
        order.setBilling(new Billing());
        when(orderMapper.orderObjectToOrder(any())).thenReturn(order);
        when(orderMapper.orderToOrderObject(any())).thenReturn(orderObject);
        when(orderRepository.save(any())).thenReturn(order);
        OrderResponse response = orderService.placeOrder(new OrderRequest(orderObject));
        assertEquals(1, response.getOrders().size());
        assertEquals("OR1234567", response.getOrders().get(0).getOrderNumber());
    }

    @Test
    void cancelOrder() {
        OrderObject orderObject = getOrderObject();
        orderObject.setStatus(StatusType.ORDER_PLACED);
        Order order = new Order();
        order.setOrderNumber("OR1234567");
        order.setStatus(new Status(1L, "any", "Placed"));
        order.setOrderDetails(new HashSet<>());
        order.setBilling(new Billing());
        when(orderMapper.orderObjectToOrder(any())).thenReturn(order);
        when(orderMapper.orderToOrderObject(any())).thenReturn(orderObject);
        when(orderRepository.save(any())).thenReturn(order);
        OrderResponse response = orderService.cancelOrder(new OrderRequest(orderObject));
        assertEquals(1, response.getOrders().size());
        assertEquals("OR1234567", response.getOrders().get(0).getOrderNumber());
    }

    @Test
    void cancelOrder_Exception() {
        OrderObject orderObject = new OrderObject();
        orderObject.setStatus(StatusType.ORDER_SHIPPED);
        ActionNotAllowedException exception = assertThrows(ActionNotAllowedException.class,
                () -> orderService.cancelOrder(new OrderRequest(orderObject)));
        assertEquals(String.format(ORDER_NOT_CANCELLED_MSG, orderObject.getStatus().getValue()), exception.getMessage());
    }

    @Test
    void updateOrderStatus() {
        Order order = new Order();
        order.setOrderNumber("OR1234567");
        order.setStatus(new Status(1L, "any", "Placed"));
        order.setOrderDetails(new HashSet<>());
        order.setBilling(new Billing());
        order.setVersion(1L);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(statusCache.getStatus(anyString(), anyString())).thenReturn(null);
        UpdateOrderRequest request = new UpdateOrderRequest(1L, StatusType.ORDER_SHIPPED, "Test User");
        OrderResponse response = orderService.updateOrderStatus(request);
        assertEquals(0, response.getOrders().size());
        assertEquals(HttpStatus.OK.name(), response.getStatus());
    }

    @Test
    void updateOrderStatus_Exception() {
        UpdateOrderRequest request = new UpdateOrderRequest();
        request.setStatus(StatusType.ORDER_CANCELLED);
        request.setOrderId(100L);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.updateOrderStatus(request));
        assertEquals(String.format(ORDER_INVALID_INPUT_MSG, request.getOrderId(),
                request.getStatus().getValue()), exception.getMessage());
    }

    private OrderObject getOrderObject() {
        OrderObject orderObject = new OrderObject();
        orderObject.setOrderNumber("OR1234567");
        OrderDetailObject orderDetailObject = new OrderDetailObject();
        ItemObject itemObject = new ItemObject();
        itemObject.setName("Sample Item");
        itemObject.setQuantity(1L);
        itemObject.setPrice(BigDecimal.TEN);
        orderDetailObject.setItem(itemObject);
        orderDetailObject.setQuantity(1L);
        orderObject.setOrderDetails(Collections.singletonList(orderDetailObject));
        orderObject.setBilling(new BillingObject());
        return orderObject;
    }
}